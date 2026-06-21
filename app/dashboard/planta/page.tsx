import { redirect } from 'next/navigation';
import { getUsuarioActual } from '@/app/lib/actions';
import {
  fetchOperacionAsignada,
  fetchOperaciones,
  fetchMonoboyas,
  fetchOpcionesPlanificacion,
} from '@/app/lib/data';
import PlantaDashboard from '@/app/ui/planta/PlantaDashboard';
import PrepararOperacionForm from '@/app/ui/planta/PrepararOperacionForm';

export default async function PlantaPage() {
  const usuario = await getUsuarioActual();
  if (!usuario) redirect('/');

  // Caso 1: ya tiene una operación en curso → dashboard con telemetría + finalizar
  const enCurso = await fetchOperacionAsignada(usuario.dni);
  if (enCurso && ['ACTIVA', 'PAUSADA'].includes(enCurso.estado)) {
    return (
      <PlantaDashboard
        operacionId={enCurso.id}
        estadoInicial={enCurso.estado as 'ACTIVA' | 'PAUSADA'}
        nombre={usuario.nombre}
      />
    );
  }

  // Caso 2: hay operaciones PLANIFICADA esperando que esta planta las prepare
  const { data: planificadas } = await fetchOperaciones({ estado: 'PLANIFICADA' });
  // Filtra por tu planta. Login todavía no manda plantaId real para usuarios sin historial
  // (gap conocido) → si viene null mostramos todas para no dejarte bloqueada/o.
  const pendientes = usuario.plantaId != null
    ? planificadas.filter((o) => o.plantaId === usuario.plantaId)
    : planificadas;

  if (pendientes.length > 0) {
    const [monoboyasDisponibles, opciones] = await Promise.all([
      fetchMonoboyas({
        ...(usuario.plantaId != null ? { plantaId: usuario.plantaId } : {}),
        estado: 'DISPONIBLE',
      }),
      fetchOpcionesPlanificacion(),
    ]);

    return (
      <PrepararOperacionForm
        operaciones={pendientes}
        monoboyas={monoboyasDisponibles}
        operadoresLancha={opciones.operadoresLancha}
        usuario={{ dni: usuario.dni, nombre: usuario.nombre }}
      />
    );
  }

  // Caso 3: no hay nada para esta planta
  return (
    <section className="p-8 max-w-lg">
      <h1 className="text-lg font-bold">Hola, {usuario.nombre}</h1>
      <p className="text-sm text-[var(--color-text-muted)] mt-2">
        No tenés ninguna operación activa ni planificada esperando preparación.
        Cuando admin planifique una nueva, aparece acá.
      </p>
    </section>
  );
}
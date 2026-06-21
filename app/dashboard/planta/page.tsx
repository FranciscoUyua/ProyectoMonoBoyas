import { redirect } from 'next/navigation';
import { getUsuarioActual } from '@/app/lib/actions';
import { fetchOperacionAsignada } from '@/app/lib/data';
import TelemetriaEnVivo from '@/app/ui/dashboard/TelemetriaEnVivo';

export default async function PlantaPage() {
  const usuario = await getUsuarioActual();
  if (!usuario) redirect('/');

  const operacion = await fetchOperacionAsignada(usuario.dni);

  if (!operacion || !['ACTIVA', 'PAUSADA'].includes(operacion.estado)) {
    return (
      <section className="p-8 max-w-lg">
        <h1 className="text-lg font-bold">Hola, {usuario.nombre}</h1>
        <p className="text-sm text-[var(--color-text-muted)] mt-2">
          No tenés ninguna operación activa asignada. Cuando se inicie una operación
          donde estés designado como operador de planta, la telemetría va a aparecer acá.
        </p>
      </section>
    );
  }

  return <TelemetriaEnVivo operacionId={operacion.id} />;
}
import { redirect } from 'next/navigation';
import { getUsuarioActual } from '@/app/lib/actions';
import {
  fetchOperacion, fetchAlertasDeOperacion, fetchSensores,
  fetchMonoboyas, fetchBuques,
} from '@/app/lib/data';
import IniciarOperacion from '@/app/ui/lancha/IniciarOperacion';
import LanchaDashboard from '@/app/ui/lancha/LanchaDashboard';

export default async function LanchaPage() {
  const usuario = await getUsuarioActual();
  if (!usuario) redirect('/');

  // Sin operación → formulario para iniciarla
  if (!usuario.operacionId) {
    const [monoboyas, buques] = await Promise.all([
      fetchMonoboyas({ estado: 'DISPONIBLE' }),
      fetchBuques(),
    ]);
    return <IniciarOperacion usuario={usuario} monoboyas={monoboyas} buques={buques} />;
  }

  // Con operación → monitoreo
  const operacion = await fetchOperacion(usuario.operacionId);
  const [alertas, sensores] = await Promise.all([
    fetchAlertasDeOperacion(operacion.id),
    fetchSensores({ monoboyaId: operacion.monoboyaId, activo: true }),
  ]);

  return (
    <LanchaDashboard
      operacion={operacion}
      sensores={sensores}
      alertasIniciales={alertas}
      nombreUsuario={usuario.nombre}
    />
  );
}

import { lusitana } from '@/app/ui/fonts';
import { getUsuarioActual } from '@/app/lib/actions';
import { fetchMonoboyas, fetchAlertas, fetchOperaciones } from '@/app/lib/data';
import Sidebar from '@/app/ui/dashboard/Sidebar';

// ─── Tarjeta de métrica ───────────────────────────────────
function MetricCard({
  label,
  value,
  color = 'text-white',
}: {
  label: string;
  value: number | string;
  color?: string;
}) {
  return (
    <div className="h-32 bg-[#1a1a1a] border border-gray-800 rounded-xl p-6">
      <span className="text-gray-500 text-xs uppercase font-bold">{label}</span>
      <p className={`text-3xl font-bold mt-2 ${color}`}>{value}</p>
    </div>
  );
}

// ─── Initiales del avatar ─────────────────────────────────
function getInitials(nombre: string): string {
  return nombre
    .split(' ')
    .slice(0, 2)
    .map((n) => n[0])
    .join('')
    .toUpperCase();
}

// ─── Página principal ─────────────────────────────────────
export default async function DashboardPage() {
  // Obtener usuario logueado
  const usuario = await getUsuarioActual();

  // Fetchear métricas del backend en paralelo
  // Promise.allSettled: si un fetch falla, los demás siguen funcionando
  const [monoboyasResult, alertasResult, operacionesResult] = await Promise.allSettled([
    fetchMonoboyas(),
    fetchAlertas({ tipo: 'CRITICA', estado: 'PENDIENTE' }),
    fetchOperaciones({ estado: 'ACTIVA' }),
  ]);

  // Extraer valores — si el fetch falló, mostrar 0 (backend no disponible todavía)
  const monoboyas     = monoboyasResult.status     === 'fulfilled' ? monoboyasResult.value     : [];
  const alertasPag    = alertasResult.status        === 'fulfilled' ? alertasResult.value        : { data: [] };
  const operacionesPag = operacionesResult.status  === 'fulfilled' ? operacionesResult.value    : { data: [] };

  const monoboyasEnOperacion = monoboyas.filter((m) => m.estado === 'EN_OPERACION').length;
  const alertasCriticas      = alertasPag.data.length;
  const barcosEnZona         = operacionesPag.data.length; // un barco por operación activa

  const nombreUsuario = usuario?.nombre ?? 'Usuario';
  const rolUsuario    = usuario?.rol    ?? '';

  return (
    <div className="relative flex h-screen bg-[#0f0f0f] text-white overflow-hidden">

      {/* Sidebar (Client Component — maneja collapse y logout) */}
      <Sidebar usuario={usuario} />

      {/* Contenido principal */}
      <main className="flex-1 flex flex-col overflow-y-auto">

        {/* Header */}
        <header className="h-16 border-b border-gray-800 flex items-center px-8 justify-between bg-[#0f0f0f]">
          <h2 className="text-gray-400 text-sm font-medium uppercase tracking-widest">
            Dashboard de Control
          </h2>
          <div className="flex items-center gap-4">
            <div className="text-right">
              <p className="text-xs text-gray-300 font-medium">{nombreUsuario}</p>
              <p className="text-xs text-gray-500">{rolUsuario.replace(/_/g, ' ')}</p>
            </div>
            <div className="w-8 h-8 rounded-full bg-blue-600 flex items-center justify-center text-xs font-bold text-white">
              {getInitials(nombreUsuario)}
            </div>
          </div>
        </header>

        {/* Contenido */}
        <section className="p-10 max-w-7xl">
          <h1 className={`${lusitana.className} text-3xl font-bold mb-4`}>
            Panel de Administración
          </h1>
          <p className="text-gray-400 leading-relaxed max-w-2xl">
            Bienvenido al centro de mando. Desde aquí podés supervisar la
            telemetría de las monoboyas en tiempo real.
          </p>

          {/* Indicador si el backend no está disponible */}
          {monoboyasResult.status === 'rejected' && (
            <div className="mt-6 px-4 py-3 bg-yellow-900/30 border border-yellow-700 rounded-lg text-yellow-400 text-sm">
              ⚠️ No se pudo conectar con el backend. Mostrando valores por defecto.
            </div>
          )}

          {/* Tarjetas de métricas */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-12">
            <MetricCard
              label="Monoboyas en Operación"
              value={monoboyasEnOperacion}
            />
            <MetricCard
              label="Alertas Críticas Pendientes"
              value={alertasCriticas}
              color={alertasCriticas > 0 ? 'text-red-500' : 'text-white'}
            />
            <MetricCard
              label="Barcos en Zona"
              value={barcosEnZona}
              color="text-blue-500"
            />
          </div>
        </section>

      </main>
    </div>
  );
}
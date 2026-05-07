'use client'; // Necesario para usar useState

import { useState } from 'react';
import { 
  Bars3Icon, 
  XMarkIcon, 
  HomeIcon, 
  UsersIcon, 
  Cog6ToothIcon, 
  ChartBarIcon,
  AnchorIcon, // Si no tienes estos, usa cualquier icono de Heroicons
  ArrowLeftOnRectangleIcon
} from '@heroicons/react/24/outline'; // Asegúrate de tener heroicons instalado
import { lusitana } from '@/app/ui/fonts';

export default function AdminPage() {
  const [isCollapsed, setIsCollapsed] = useState(false);

  return (
    <div className="flex h-screen bg-[#0f0f0f] text-white overflow-hidden">
      
      {/* 1. SIDEBAR (La barra lateral) */}
      <aside 
        className={`bg-[#1a1a1a] transition-all duration-300 ease-in-out flex flex-col border-r border-gray-800 ${
          isCollapsed ? 'w-20' : 'w-64'
        }`}
      >
        {/* Botón de colapso y Logo */}
        <div className="p-4 flex items-center justify-between h-16">
          {!isCollapsed && <span className={`${lusitana.className} text-xl font-bold text-blue-500`}>VIGÍA</span>}
          <button 
            onClick={() => setIsCollapsed(!isCollapsed)}
            className="p-2 hover:bg-gray-800 rounded-lg transition-colors"
          >
            <Bars3Icon className="w-6 h-6 text-gray-400" />
          </button>
        </div>

        {/* Links de navegación */}
        <nav className="flex-1 mt-4 px-3 space-y-2">
          <NavItem icon={<HomeIcon className="w-6 h-6" />} label="Inicio" isCollapsed={isCollapsed} active />
          <NavItem icon={<ChartBarIcon className="w-6 h-6" />} label="Operaciones" isCollapsed={isCollapsed} />
          <NavItem icon={<UsersIcon className="w-6 h-6" />} label="Personal" isCollapsed={isCollapsed} />
          <NavItem icon={<Cog6ToothIcon className="w-6 h-6" />} label="Configuración" isCollapsed={isCollapsed} />
        </nav>

        {/* Footer del Sidebar (Cerrar sesión) */}
        <div className="p-4 border-t border-gray-800">
          <button className="flex items-center gap-4 w-full p-2 hover:bg-red-900/20 text-red-500 rounded-lg transition-colors">
            <ArrowLeftOnRectangleIcon className="w-6 h-6" />
            {!isCollapsed && <span className="font-medium text-sm">Cerrar Sesión</span>}
          </button>
        </div>
      </aside>

      {/* 2. CONTENIDO PRINCIPAL */}
      <main className="flex-1 flex flex-col overflow-y-auto">
        {/* Header superior opcional */}
        <header className="h-16 border-b border-gray-800 flex items-center px-8 justify-between bg-[#0f0f0f]">
          <h2 className="text-gray-400 text-sm font-medium uppercase tracking-widest">Dashboard de Control</h2>
          <div className="flex items-center gap-4">
            <span className="text-xs text-gray-500">Admin: Sánchez Juan</span>
            <div className="w-8 h-8 rounded-full bg-blue-600 flex items-center justify-center text-xs font-bold text-white">JS</div>
          </div>
        </header>

        {/* Área de contenido */}
        <section className="p-10 max-w-7xl">
          <h1 className={`${lusitana.className} text-3xl font-bold mb-4`}>Panel de Administración</h1>
          <p className="text-gray-400 leading-relaxed max-w-2xl">
            Bienvenido al centro de mando. Desde aquí puedes supervisar la telemetría de las monoboyas, 
            gestionar los acoples de los buques y administrar los permisos del personal de operaciones.
          </p>

          {/* Espacio para tus futuras tarjetas/gráficos */}
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-12">
            <div className="h-32 bg-[#1a1a1a] border border-gray-800 rounded-xl p-6">
              <span className="text-gray-500 text-xs uppercase font-bold">Monoboyas Activas</span>
              <p className="text-3xl font-bold mt-2">12</p>
            </div>
            <div className="h-32 bg-[#1a1a1a] border border-gray-800 rounded-xl p-6">
              <span className="text-gray-500 text-xs uppercase font-bold">Alertas Críticas</span>
              <p className="text-3xl font-bold mt-2 text-red-500">0</p>
            </div>
            <div className="h-32 bg-[#1a1a1a] border border-gray-800 rounded-xl p-6">
              <span className="text-gray-500 text-xs uppercase font-bold">Barcos en Zona</span>
              <p className="text-3xl font-bold mt-2 text-blue-500">4</p>
            </div>
          </div>
        </section>
      </main>
    </div>
  );
}

/** * Pequeño componente interno para los links del menú 
 */
function NavItem({ icon, label, isCollapsed, active = false }: { 
  icon: React.ReactNode, 
  label: string, 
  isCollapsed: boolean,
  active?: boolean 
}) {
  return (
    <a 
      href="#" 
      className={`flex items-center gap-4 p-2 rounded-lg transition-all ${
        active ? 'bg-blue-600 text-white' : 'text-gray-400 hover:bg-gray-800 hover:text-white'
      }`}
    >
      <div className="min-w-[24px]">{icon}</div>
      {!isCollapsed && <span className="font-medium text-sm whitespace-nowrap">{label}</span>}
    </a>
  );
}
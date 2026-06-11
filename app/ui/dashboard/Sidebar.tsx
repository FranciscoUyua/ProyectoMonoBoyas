'use client';

import { useState } from 'react';
import { useFormStatus } from 'react-dom';
import {
  Bars3Icon,
  HomeIcon,
  UsersIcon,
  Cog6ToothIcon,
  SignalIcon,
  LifebuoyIcon,
  ArrowLeftOnRectangleIcon,
} from '@heroicons/react/24/outline';
import { lusitana } from '@/app/ui/fonts';
import { logout } from '@/app/lib/actions';
import type { Usuario } from '@/app/lib/definitions';

// ─── Botón de logout con estado de carga ─────────────────
function LogoutButton() {
  const { pending } = useFormStatus();
  return (
    <button
      type="submit"
      disabled={pending}
      className="flex items-center gap-4 w-full p-2 hover:bg-[var(--color-danger-soft)] text-[var(--color-alerta-rojo)] rounded-lg transition-colors disabled:opacity-50"
    >
      <ArrowLeftOnRectangleIcon className="w-6 h-6" />
      <span className="font-medium text-sm">
        {pending ? 'Saliendo...' : 'Cerrar Sesión'}
      </span>
    </button>
  );
}

// ─── Item de navegación ───────────────────────────────────
function NavItem({
  icon,
  label,
  isCollapsed,
  active = false,
}: {
  icon: React.ReactNode;
  label: string;
  isCollapsed: boolean;
  active?: boolean;
}) {
  return (
    <a
      href="#"
      className={`flex items-center gap-4 p-2 rounded-lg transition-all ${
        active
          ? 'bg-[var(--color-primary)] text-[var(--color-text)]'
          : 'text-[var(--color-text-muted)] hover:bg-[var(--color-border)] hover:text-[var(--color-text)]'
      }`}
    >
      <div className="min-w-[24px]">{icon}</div>
      {!isCollapsed && (
        <span className="font-medium text-sm whitespace-nowrap">{label}</span>
      )}
    </a>
  );
}

// ─── Sidebar principal ────────────────────────────────────
export default function Sidebar({ usuario }: { usuario: Usuario | null }) {
  const [isCollapsed, setIsCollapsed] = useState(false);
  const [showLogoutModal, setShowLogoutModal] = useState(false);

  return (
    <>
      <aside
        className={`bg-[var(--color-surface)] transition-all duration-300 ease-in-out flex flex-col border-r border-[var(--color-border)] ${
          isCollapsed ? 'w-20' : 'w-64'
        }`}
      >
        {/* Logo + toggle */}
        <div className="p-4 flex items-center justify-between h-16">
          {!isCollapsed && (
            <span className={`${lusitana.className} text-xl font-bold text-[var(--color-primary-soft)]`}>
              VIGÍA
            </span>
          )}
          <button
            onClick={() => setIsCollapsed(!isCollapsed)}
            className="p-2 hover:bg-[var(--color-border)] rounded-lg transition-colors"
          >
            <Bars3Icon className="w-6 h-6 text-[var(--color-text-muted)]" />
          </button>
        </div>

        {/* Navegación */}
        <nav className="flex-1 mt-4 px-3 space-y-2">
          <NavItem icon={<HomeIcon className="w-6 h-6" />}        label="Inicio"         isCollapsed={isCollapsed} active />
          <NavItem icon={<SignalIcon className="w-6 h-6" />}      label="Monoboyas"      isCollapsed={isCollapsed} />
          <NavItem icon={<LifebuoyIcon className="w-6 h-6" />}    label="Operaciones"    isCollapsed={isCollapsed} />
          <NavItem icon={<UsersIcon className="w-6 h-6" />}       label="Personal"       isCollapsed={isCollapsed} />
          <NavItem icon={<Cog6ToothIcon className="w-6 h-6" />}   label="Configuración"  isCollapsed={isCollapsed} />
        </nav>

        {/* Cerrar sesión */}
        <div className="p-4 border-t border-[var(--color-border)]">
          {isCollapsed ? (
            <button
              onClick={() => setShowLogoutModal(true)}
              className="flex justify-center w-full p-2 hover:bg-[var(--color-danger-soft)] text-[var(--color-alerta-rojo)] rounded-lg transition-colors"
            >
              <ArrowLeftOnRectangleIcon className="w-6 h-6" />
            </button>
          ) : (
            <button
              onClick={() => setShowLogoutModal(true)}
              className="flex items-center gap-4 w-full p-2 hover:bg-[var(--color-danger-soft)] text-[var(--color-alerta-rojo)] rounded-lg transition-colors"
            >
              <ArrowLeftOnRectangleIcon className="w-6 h-6" />
              <span className="font-medium text-sm">Cerrar Sesión</span>
            </button>
          )}
        </div>
      </aside>

      {/* Modal de confirmación de logout */}
      {showLogoutModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 backdrop-blur-sm p-4">
          <div className="bg-[var(--color-surface)] border border-[var(--color-border)] w-full max-w-sm p-8 rounded-sm shadow-2xl">
            <h3 className="text-xl font-bold mb-2 text-[var(--color-text)]">¿Cerrar sesión?</h3>
            <p className="text-[var(--color-text-muted)] text-sm mb-8">
              Estás a punto de salir del sistema de control. ¿Confirmás que querés
              finalizar la sesión actual?
            </p>
            <div className="flex gap-4 justify-end">
              <button
                onClick={() => setShowLogoutModal(false)}
                className="px-4 py-2 text-sm font-medium text-[var(--color-text-muted)] hover:text-[var(--color-text)] transition-colors"
              >
                Cancelar
              </button>
              {/* form action llama al server action logout() */}
              <form action={logout}>
                <LogoutButton />
              </form>
            </div>
          </div>
        </div>
      )}
    </>
  );
}
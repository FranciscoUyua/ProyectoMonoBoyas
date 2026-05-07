import Image from 'next/image';
import { authenticate } from '@/app/lib/actions';

export default function Page() {
  return (
    <main className="relative flex min-h-screen items-center justify-center overflow-hidden">
      
      {/* 1. Fondo con la imagen */}
      <div className="absolute inset-0 z-0">
        <Image
          src="/fondo_barco.png" 
          alt="Fondo Buque"
          fill
          className="object-cover brightness-[0.35]"
          priority
        />
      </div>

      {/* 2. Tarjeta de Login centrada */}
      <div className="z-10 w-full max-w-[400px] rounded-sm bg-[#1a1a1a]/90 p-10 shadow-2xl backdrop-blur-sm flex flex-col">
        
        {/* Título centrado */}
        <div className="mb-10 text-center">
          <h2 className="text-xl font-bold text-white uppercase tracking-widest">
            Iniciar sesión
          </h2>
        </div>

        {/* 3. Formulario con la acción de redirección */}
        <form action={authenticate} className="space-y-8">
          <div className="group text-left">
            <label className="block text-xs text-gray-500 mb-1 transition-colors group-focus-within:text-blue-500">
              Usuario
            </label>
            <input
              type="text"
              name="username" // Importante: nombre para identificar el campo
              className="w-full bg-transparent border-b border-gray-700 py-1 text-white focus:outline-none focus:border-blue-500 transition-colors"
              required
            />
          </div>

          <div className="group text-left">
            <label className="block text-xs text-gray-500 mb-1 transition-colors group-focus-within:text-blue-500">
              Contraseña
            </label>
            <input
              type="password"
              name="password" // Importante: nombre para identificar el campo
              className="w-full bg-transparent border-b border-gray-700 py-1 text-white focus:outline-none focus:border-blue-500 transition-colors"
              required
            />
          </div>

          <div className="text-right">
            <a href="#" className="text-xs text-blue-500 hover:text-blue-400 transition-colors">
              ¿Olvidaste tu contraseña?
            </a>
          </div>

          <button 
            type="submit"
            className="w-full bg-[#0070f3] hover:bg-blue-600 py-3 text-sm font-semibold text-white rounded-sm transition-all duration-300"
          >
            Ingresar
          </button>
        </form>

        {/* Enlace inferior centrado */}
        <div className="mt-10 text-center text-xs">
          <span className="text-gray-500 text-[11px]">¿Nuevo usuario? </span>
          <a href="#" className="text-blue-500 hover:text-blue-400 font-medium ml-1">
            Solicitar acceso
          </a>
        </div>
      </div>

      {/* 4. Footer */}
      <div className="absolute bottom-6 right-10 flex gap-6 text-[10px] uppercase tracking-wider text-gray-400 font-medium">
        <a href="#" className="hover:text-white transition-colors">Ayuda y contacto</a>
        <a href="#" className="hover:text-white transition-colors">Términos de uso</a>
        <a href="#" className="hover:text-white transition-colors">Privacidad y cookies</a>
      </div>
    </main>
  );
}
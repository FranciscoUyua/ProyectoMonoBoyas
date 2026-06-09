import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';

export function middleware(request: NextRequest) {
  const token = request.cookies.get('auth_token')?.value;
  const estaEnLogin     = request.nextUrl.pathname === '/';
  const estaEnDashboard = request.nextUrl.pathname.startsWith('/dashboard');

  // Sin token intentando entrar al dashboard → mandarlo al login
  if (estaEnDashboard && !token) {
    return NextResponse.redirect(new URL('/', request.url));
  }

  // Con token intentando entrar al login → mandarlo al dashboard
  if (estaEnLogin && token) {
    return NextResponse.redirect(new URL('/dashboard', request.url));
  }

  return NextResponse.next();
}

export const config = {
  matcher: ['/', '/dashboard/:path*'],
};
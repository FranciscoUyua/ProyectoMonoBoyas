import { cookies } from 'next/headers';
import { NextRequest, NextResponse } from 'next/server';

const API = process.env.API_URL;

export async function POST(request: NextRequest) {
  const cookieStore = await cookies();
  const token = cookieStore.get('auth_token')?.value;
  if (!token) return NextResponse.json({ error: 'No autenticado' }, { status: 401 });

  const body = await request.json();
  const res = await fetch(`${API}/operaciones`, {
    method: 'POST',
    headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' },
    body: JSON.stringify(body),
  });
  const data = await res.json();
  if (!res.ok) return NextResponse.json(data, { status: res.status });

  // El operador queda asignado a la operación recién creada:
  // actualizamos user_data para que la página muestre el monitoreo
  const userRaw = cookieStore.get('user_data')?.value;
  if (userRaw) {
    try {
      const user = JSON.parse(userRaw);
      user.operacionId = data.id;
      cookieStore.set('user_data', JSON.stringify(user), { path: '/' });
    } catch { /* ignore */ }
  }
  return NextResponse.json(data, { status: 201 });
}
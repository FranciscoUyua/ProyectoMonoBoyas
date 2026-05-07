'use server'; 

import { redirect } from 'next/navigation';

export async function authenticate(formData: FormData) {
  // No importa lo que el usuario escriba, lo enviamos al dashboard
  redirect('/dashboard');
}
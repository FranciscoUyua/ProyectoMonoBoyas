'use client';

import { useState, useEffect } from 'react';

// ─── Tipos ────────────────────────────────────────────────────
type Nivel = 'verde' | 'amarillo' | 'rojo';

interface Sensor {
  id: string;
  label: string;
  unidad: string;
  valor: number;
  base: number;   // valor cómodo al que tiende
  min: number;
  max: number;
  amarillo: number; // umbral precaución
  rojo: number;     // umbral crítico
  ruido: number;
  dec: number;      // decimales a mostrar
}

// ─── Definición de sensores ───────────────────────────────────
const SENSORES_INIT: Sensor[] = [
  { id: 'presion',   label: 'Presión de transferencia', unidad: 'bar',  valor: 9.2,  base: 9.5,  min: 0, max: 20,   amarillo: 14,   rojo: 16,   ruido: 0.45, dec: 1 },
  { id: 'viento',    label: 'Velocidad de viento',      unidad: 'kn',   valor: 16,   base: 18,   min: 0, max: 45,   amarillo: 22,   rojo: 32,   ruido: 1.6,  dec: 0 },
  { id: 'ola',       label: 'Altura de ola',            unidad: 'm',    valor: 1.2,  base: 1.4,  min: 0, max: 4,    amarillo: 1.8,  rojo: 2.6,  ruido: 0.16, dec: 2 },
  { id: 'corriente', label: 'Velocidad de corriente',   unidad: 'kn',   valor: 1.3,  base: 1.5,  min: 0, max: 5,    amarillo: 2.2,  rojo: 3.2,  ruido: 0.18, dec: 1 },
  { id: 'caudal',    label: 'Caudal de transferencia',  unidad: 'm³/h', valor: 1050, base: 1080, min: 0, max: 1600, amarillo: 1400, rojo: 1500, ruido: 38,   dec: 0 },
  { id: 'amarre',    label: 'Tensión de amarre',        unidad: 't',    valor: 44,   base: 46,   min: 0, max: 90,   amarillo: 62,   rojo: 75,   ruido: 2.8,  dec: 0 },
];

const HIST_LEN = 28;

function nivel(s: Sensor): Nivel {
  if (s.valor >= s.rojo) return 'rojo';
  if (s.valor >= s.amarillo) return 'amarillo';
  return 'verde';
}

const COLOR_NIVEL: Record<Nivel, string> = {
  verde:    'var(--color-alerta-verde)',
  amarillo: 'var(--color-alerta-amarillo)',
  rojo:     'var(--color-alerta-rojo)',
};
const TEXTO_NIVEL: Record<Nivel, string> = { verde: 'NORMAL', amarillo: 'PRECAUCIÓN', rojo: 'CRÍTICO' };

// ─── Sparkline ────────────────────────────────────────────────
function Sparkline({ data, min, max, color }: { data: number[]; min: number; max: number; color: string }) {
  const W = 100, H = 30;
  if (data.length < 2) return <svg viewBox={`0 0 ${W} ${H}`} className="w-full h-8" />;
  const rango = max - min || 1;
  const pts = data.map((v, i) => {
    const x = (i / (data.length - 1)) * W;
    const y = H - ((v - min) / rango) * H;
    return `${x.toFixed(1)},${Math.max(1, Math.min(H - 1, y)).toFixed(1)}`;
  });
  const ultimo = pts[pts.length - 1].split(',');
  return (
    <svg viewBox={`0 0 ${W} ${H}`} preserveAspectRatio="none" className="w-full h-8">
      <polyline
        points={pts.join(' ')}
        fill="none"
        stroke={color}
        strokeWidth="1.5"
        strokeLinejoin="round"
        strokeLinecap="round"
        vectorEffect="non-scaling-stroke"
      />
      <circle cx={ultimo[0]} cy={ultimo[1]} r="1.8" fill={color} />
    </svg>
  );
}

// ─── Instrumento (tarjeta de sensor) ──────────────────────────
function Instrumento({ s, hist }: { s: Sensor; hist: number[] }) {
  const n = nivel(s);
  const color = COLOR_NIVEL[n];
  const critico = n === 'rojo';
  return (
    <div
      className="bg-[var(--color-surface)] rounded-xl p-5 flex flex-col gap-3 border transition-colors"
      style={{
        borderColor: critico ? 'var(--color-alerta-rojo)' : 'var(--color-border)',
        boxShadow: critico ? '0 0 0 1px var(--color-alerta-rojo), 0 0 24px -8px var(--color-alerta-rojo)' : 'none',
      }}
    >
      <div className="flex items-start justify-between">
        <span className="text-[10px] uppercase tracking-widest font-bold text-[var(--color-text-faint)] leading-tight max-w-[70%]">
          {s.label}
        </span>
        <span className="flex items-center gap-1.5 text-[10px] font-bold tracking-wider" style={{ color }}>
          <span className="w-1.5 h-1.5 rounded-full" style={{ backgroundColor: color, boxShadow: `0 0 6px ${color}` }} />
          {TEXTO_NIVEL[n]}
        </span>
      </div>

      <div className="flex items-baseline gap-1.5">
        <span
          className="text-4xl font-bold font-mono tabular-nums tracking-tight transition-colors"
          style={{ color: critico ? 'var(--color-alerta-rojo)' : 'var(--color-text)' }}
        >
          {s.valor.toFixed(s.dec)}
        </span>
        <span className="text-sm font-mono text-[var(--color-text-muted)]">{s.unidad}</span>
      </div>

      <Sparkline data={hist} min={s.min} max={s.max} color={color} />

      <div className="flex justify-between text-[10px] font-mono text-[var(--color-text-faint)]">
        <span>umbral {s.amarillo}{s.unidad === 'm³/h' ? '' : s.unidad}</span>
        <span>máx {s.max}</span>
      </div>
    </div>
  );
}

// ─── Métrica resumen ──────────────────────────────────────────
function Resumen({ label, value, color }: { label: string; value: number | string; color?: string }) {
  return (
    <div className="bg-[var(--color-surface)] border border-[var(--color-border)] rounded-xl px-5 py-4 flex flex-col gap-1">
      <span className="text-[10px] uppercase tracking-widest font-bold text-[var(--color-text-faint)]">{label}</span>
      <span className="text-3xl font-bold font-mono tabular-nums" style={{ color: color || 'var(--color-text)' }}>{value}</span>
    </div>
  );
}

// ─── Componente principal ─────────────────────────────────────
export default function TelemetriaEnVivo() {
  const [sensores, setSensores] = useState<Sensor[]>(SENSORES_INIT);
  const [hist, setHist] = useState<Record<string, number[]>>(() =>
    Object.fromEntries(SENSORES_INIT.map((s) => [s.id, Array(HIST_LEN).fill(s.valor)]))
  );
  const [hora, setHora] = useState<Date | null>(null);

  // Reloj (arranca en el cliente para no romper la hidratación)
  useEffect(() => {
    setHora(new Date());
    const t = setInterval(() => setHora(new Date()), 1000);
    return () => clearInterval(t);
  }, []);

  // Simulación de telemetría — reemplazar por backend (polling / WebSocket / MQTT)
  useEffect(() => {
    const t = setInterval(() => {
      setSensores((prev) =>
        prev.map((s) => {
          const ruido = (Math.random() - 0.5) * 2 * s.ruido;
          const reversion = (s.base - s.valor) * 0.08;           // tiende a su base
          const evento = Math.random() < 0.04 ? s.ruido * 3 : 0; // pico esporádico
          let v = s.valor + ruido + reversion + evento;
          v = Math.max(s.min, Math.min(s.max, v));
          return { ...s, valor: v };
        })
      );
    }, 1500);
    return () => clearInterval(t);
  }, []);

  // Empujar al historial cuando cambian los valores
  useEffect(() => {
    setHist((prev) => {
      const next = { ...prev };
      sensores.forEach((s) => {
        next[s.id] = [...prev[s.id].slice(1), s.valor];
      });
      return next;
    });
  }, [sensores]);

  const criticos = sensores.filter((s) => nivel(s) === 'rojo');
  const enPrecaucion = sensores.filter((s) => nivel(s) === 'amarillo');

  return (
    <section className="p-8 max-w-7xl w-full">
      {/* Banner de alerta crítica */}
      {criticos.length > 0 && (
        <div
          className="mb-6 px-4 py-3 rounded-lg border text-sm flex items-center gap-3"
          style={{ backgroundColor: 'rgba(239,68,68,0.12)', borderColor: 'var(--color-alerta-rojo)', color: 'var(--color-alerta-rojo)' }}
        >
          <span className="w-2 h-2 rounded-full bg-[var(--color-alerta-rojo)] animate-pulse" />
          {criticos.length} condición{criticos.length > 1 ? 'es' : ''} crítica{criticos.length > 1 ? 's' : ''}: {criticos.map((s) => s.label).join(', ')}. Requiere reconocimiento del operador.
        </div>
      )}

      {/* Resumen */}
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-10">
        <Resumen label="Monoboyas en operación" value="1" />
        <Resumen label="Alertas críticas" value={criticos.length} color={criticos.length ? 'var(--color-alerta-rojo)' : undefined} />
        <Resumen label="En precaución" value={enPrecaucion.length} color={enPrecaucion.length ? 'var(--color-alerta-amarillo)' : undefined} />
        <Resumen label="Barcos en zona" value="1" color="var(--color-primary-soft)" />
      </div>

      {/* Encabezado de telemetría */}
      <div className="flex items-center gap-3 mb-5">
        <h1 className="text-lg font-bold tracking-tight">Telemetría en vivo</h1>
        <span className="flex items-center gap-1.5 text-[11px] font-bold tracking-wider text-[var(--color-alerta-verde)]">
          <span className="w-1.5 h-1.5 rounded-full bg-[var(--color-alerta-verde)] animate-pulse" />
          EN VIVO
        </span>
        <span className="h-px flex-1 bg-[var(--color-border)]" />
        <span className="font-mono text-xs text-[var(--color-text-muted)] tabular-nums">
          {hora ? hora.toLocaleTimeString('es-AR') : '--:--:--'}
        </span>
        <span className="text-[10px] uppercase tracking-widest text-[var(--color-text-faint)] font-mono">
          · 1.5s
        </span>
      </div>

      {/* Grilla de instrumentos */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        {sensores.map((s) => (
          <Instrumento key={s.id} s={s} hist={hist[s.id]} />
        ))}
      </div>
    </section>
  );
}
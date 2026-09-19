# printtray

## Purpose

Agent local de impressão (fork QZ Tray) para o ERP: PDF silencioso só em localhost.

## Ownership

- `ant/project.properties` — branding `printtray` / Boa Gestão
- `.github/workflows/release.yml` — bump + build Windows + GitHub Release (só `master`)

## Local Contracts

- Portas fixas: **37241** (ws) / **37242** (wss). Sincronizar com `frontend/src/lib/printtray/types.ts`.
- Status: `GET http://127.0.0.1:37241/status`
- Frontend: `frontend/src/lib/printtray`
- PDF de etiqueta: `PrintPDF` aplica `options.size` como tamanho real da folha (`paper.setSize`); `pageWidth`/`pageHeight` no item PDF é o workaround do QZ para Microsoft Print to PDF.
- Artefato de release: `out/printtray-<versão>-x86_64.exe`
- Push em `master` → bump de versão + release automático (`[skip ci]` no commit de bump)
- LGPL 2.1: manter source aberto ao redistribuir o binário.

## Work Guidance

- Não reabrir bind em `0.0.0.0` sem revisar o modelo de ameaça.
- Não reintroduzir exigência de certificado para loopback.
- Ao mudar a porta, atualizar Constants **e** o cliente no frontend.
- Não adicionar CI em branches/PRs; release só via push em `master`.

## Verification

- Subir o agent → `curl http://127.0.0.1:37241/status`
- Conectar `ws://127.0.0.1:37241` e imprimir PDF sem diálogo
- Confirmar que conexão de outra máquina falha
- Push em `master` gera release com `out/printtray-*-x86_64.exe`

## Child DOX Index

(nenhum)

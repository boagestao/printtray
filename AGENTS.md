# printtray

## Purpose

Agent local de impressão (fork QZ Tray) para o ERP: PDF silencioso só em localhost.

## Ownership

- `ant/project.properties` — branding `printtray` / Boa Gestão
- `.github/workflows/build-windows.yml` — CI Windows
- `.github/workflows/release-windows.yml` — release no GitHub (tag `v*`)

## Local Contracts

- Portas fixas: **37241** (ws) / **37242** (wss). Sincronizar com `frontend/src/lib/printtray/types.ts`.
- Status: `GET http://127.0.0.1:37241/status`
- Frontend: `frontend/src/lib/printtray`
- Artefato de release: `out/printtray-<versão>-x86_64.exe`
- LGPL 2.1: manter source aberto ao redistribuir o binário.

## Work Guidance

- Não reabrir bind em `0.0.0.0` sem revisar o modelo de ameaça.
- Não reintroduzir exigência de certificado para loopback.
- Ao mudar a porta, atualizar Constants **e** o cliente no frontend.
- Release: tag `vX.Y.Z` (ou `workflow_dispatch`); o CI grava a versão em `Constants.VERSION`.

## Verification

- Subir o agent → `curl http://127.0.0.1:37241/status`
- Conectar `ws://127.0.0.1:37241` e imprimir PDF sem diálogo
- Confirmar que conexão de outra máquina falha
- CI: `ant -Dtarget.arch=x86_64 nsis` gera `out/printtray-*-x86_64.exe`

## Child DOX Index

(nenhum)

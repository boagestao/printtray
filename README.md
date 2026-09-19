# PrintTray

Fork do [QZ Tray](https://github.com/qzind/tray) (LGPL 2.1) adaptado para impressão local silenciosa no ERP Boa Gestão.

## Comportamento deste fork

- **Somente localhost**: bind em `127.0.0.1`; conexões remotas são rejeitadas.
- **Impressão silenciosa**: sem diálogo de certificado/assinatura para clientes em loopback.
- **Portas fixas do projeto** (não aleatórias em runtime):
  - WebSocket: `ws://127.0.0.1:37241`
  - WSS (opcional): `37242`
- File IO / print-to-host / print-to-file desabilitados por padrão.

## Status JSON

```http
GET http://127.0.0.1:37241/status
```

Exemplo:

```json
{
  "ok": true,
  "name": "PrintTray",
  "localhostOnly": true,
  "silentPrint": true,
  "websocket": { "host": "127.0.0.1", "insecure": 37241, "secure": 37242 }
}
```

## Build / Release

### Local (Windows)

Requisitos: JDK 25 (Liberica), Apache Ant, NSIS.

```bat
ant -Dtarget.arch=x86_64 nsis
```

Instalador em `out/printtray-<versão>-x86_64.exe`.

### GitHub Actions

Workflow único: `.github/workflows/release.yml` (somente **`master`**).

A cada push em `master`:

1. Faz bump automático de `Constants.VERSION` (remove `-SNAPSHOT` ou incrementa o patch)
2. Compila o instalador Windows (`printtray-<versão>-x86_64.exe`)
3. Commita a versão com `[skip ci]`, cria a tag `vX.Y.Z` e publica no **GitHub Releases**

## Licença

LGPL 2.1 — ver `LICENSE.txt`. Este fork mantém o código aberto.

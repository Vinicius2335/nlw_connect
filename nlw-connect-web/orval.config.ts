import { defineConfig } from 'orval'

export default defineConfig({
  api: {
    input: 'http://localhost:8080/swagger/docs',

    output: {
      target: './src/http/api.ts',
      //local onde esse arquivo será criado

      client: 'fetch',
      httpClient: 'fetch',

      clean: true,
      // se já tiver um arquivo substitui pelo anterior para evitar conflito

      baseUrl: 'http://localhost:8080',

      override: {
        fetch: {
          includeHttpResponseReturnType: false,
          // usado quando usamos fetch
          //  o tipo de retorno da resposta HTTP não será incluído no código gerado.
          // só retorna o dado
        },
      },
    },
  },
})

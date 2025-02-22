# Anotações

## Bibliotecas Instaladas

````bash
npx create-next-app@latest --empty
√ What is your project named? ... nlw-connect-web
√ Would you like to use TypeScript? ... No / (Yes)
√ Would you like to use ESLint? ... (No) / Yes
√ Would you like to use Tailwind CSS? ... (No) / Yes
√ Would you like your code inside a `src/` directory? ... No / (Yes)
√ Would you like to use App Router? (recommended) ... No / (Yes)
√ Would you like to use Turbopack for `next dev`? ... No / (Yes)
√ Would you like to customize the import alias (`@/*` by default)? ... (No) / Yes

npm i @biomejs/biome -D
npm i lucide-react
npm i tailwind-merge
npm i react-hook-form zod @hookform/resolvers
npm i orval -D
npx orval
````
- No pra Tailwind CSS pq vem na v3 e agora existe a v4 com a config de inicializaçao simplificado e +

- Componentes - Quando separar ?
- quando algo se repete muito
- quando algo nao tem relaçao com outro na mesma pagina, dividindo por interesse
- separar por funcionalidade
- deixar o que for somente texto

- transformar um component em asyn para realizar o fetch de dados nao funciona quando ele está assinalado com o "use client" no topo
- uma forma de contornar isso seria realizar o fetch no componente pai e envia o response para o component

- somente arquivos page.tsx podem recuperar os parametros nas rotas

## Rotas

1. Subscription Event
  - POST: ``http://localhost:8080/subscription/{prettyName}``

2. Subscription Event by Indication
  - POST: ``http://localhost:8080/subscription/{prettyName}/{userId}``

3. Get TOP 3 Indication Ranking by PrettyName
  - GET: ``http://localhost:8080/subscription/{prettyName}/ranking``
  
4. Get User Indications Count by UserId
  - GET: ``http://localhost:8080/subscription/{prettyName}/ranking/{userId}``
  

##  Aula 03 -> 22:46

## TO-DO

1. Back
  - [x] add Swagger
  - [] validate request
  - [x] Alterar os endpoints name "/subscriptions" e "/events"

2. Front
   - [] add Alerts
# Anotações

````bash
➜ nlw18_connect (main) ✗ npx create-next-app@latest --empty
√ What is your project named? ... nlw-connect-web
√ Would you like to use TypeScript? ... No / (Yes)
√ Would you like to use ESLint? ... (No) / Yes
√ Would you like to use Tailwind CSS? ... (No) / Yes
√ Would you like your code inside a `src/` directory? ... No / (Yes)
√ Would you like to use App Router? (recommended) ... No / (Yes)
√ Would you like to use Turbopack for `next dev`? ... No / (Yes)
√ Would you like to customize the import alias (`@/*` by default)? ... (No) / Yes
````
- No pra Tailwind CSS pq vem na v3 e agora existe a v4 com a config de inicializaçao simplificado e +

- Componentes - Quando separar ?
- quando algo se repete muito
- quando algo nao tem relaçao com outro na mesma pagina, dividindo por interesse
- separar por funcionalidade
- deixar o que for somente texto

# Rotas

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
  - [] add Swagger
  - [] validate request
  - [] contabilizar quantas pessoas acessaram o nosso link de convite e redirecionar para o
  - [] Alterar os endpoints name "/subscriptions" e "/events"

2. Front
   - [] add Alerts
# backendRestAPI_JSON

Para testar com Heroku e Postman:
Esta API recebe uma request em formato JSON no padrão abaixo, cria um objeto Terminal e retorna este objeto no formato JSON através do método POST na URI https://backend-rest-api-json.herokuapp.com/v1/terminal/:
{
  "serial": "123",
  "model": "PWWIN",
  "sam": 0,
  "ptid": "F04A2E4088B",
  "plat": 4,
  "version": "8.00b3",
  "mxr": 0,
  "mxf": 16777216,
  "verfm": "PWWIN"
}

Esta API atualiza um objeto Terminal recebendo uma request em formato JSON no formato abaixo e um número inteiro referente à um logic de um objeto Terminal existente e retorna 
este objeto no formato JSON através do método PUT na URI https://backend-rest-api-json.herokuapp.com/v1/terminal/{logic}:
{
  "serial": "124",
  "model": "PWWIN",
  "sam": 1,
  "ptid": "F04A2E4088C",
  "plat": 4,
  "version": "8.00b3",
  "mxr": 1,
  "mxf": 16777216,
  "verfm": "PWWIZ"
}

Esta API retorna um objeto Terminal já criado no formato JSON recebendo um número inteiro referente ao logic de um objeto Terminal existente através do método GET na 
URI https://backend-rest-api-json.herokuapp.com/v1/terminal/{logic} 

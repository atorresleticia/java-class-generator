const jsonServer = require('json-server');
const faker = require('faker');

const server = jsonServer.create();
const router = jsonServer.router('db.json');
const middlewares = jsonServer.defaults();

server.use(middlewares);

/* variacoes de rotas que podem ser utilizadas */
server.use(jsonServer.rewriter({
    '/api/v1/*': '/$1',
    '/:calendar/users-profile': '/user/users/7636f630-2358-4cf4-a290-2ec0b13ea47c',
    '/:calendar/:resource/users-profile': '/user/users/7636f630-2358-4cf4-a290-2ec0b13ea47c',
    '/:calendar/:resource': '/:resource',
    '/:calendar/:resource/:id': '/:resource/:id',
    '/:calendar/:resource/*/': '/:resource/$1/'
}));

server.use(jsonServer.bodyParser);

/* rotas customizadas devem ser inseridas aqui */
router.render = (req, res) => {
    let version = req.originalUrl.split('/')[2];
    let serviceName = req.originalUrl.split('/')[3];
    let resourceName = req.originalUrl.split('/')[4];
    let resourceId = req.originalUrl.split('/')[5];
    console.log('---------------------------------');
    console.log('URL ACESSADA:' + req.originalUrl);
    console.log('VERSÃO DO SERVIÇO: ' + version);
    console.log('SERVIÇO UTILIZADO: ' + serviceName);
    console.log('RECURSO UTILIZADO: ' + resourceName);
    console.log('RECURSO BUSCADO: ' + resourceId);

    if ((req.method === 'POST' || req.method === 'PUT')  && resourceId !== 'search') {

        if (req.method === 'POST') {
            req.body.id = faker.random.uuid();
        }

        res.send({id: res.locals.data.id});
    }else if (req.method === 'GET' && resourceName !== 'users-profile' && resourceId === undefined
                || (req.method === 'POST' && resourceId === 'search')) {

        const itens = res.locals.data.length ? res.locals.data : [];
        res.status(200).send({
            items: itens,
            count: itens.length,
            page: 0,
            pageSize: 500
        });
    } else {
        /* qualquer outra rota tem resposta padrao*/
        res.send(res.locals.data);
    }

};

server.use(router);
server.listen(3000, () => {
  console.log('JSON Server is running')
});

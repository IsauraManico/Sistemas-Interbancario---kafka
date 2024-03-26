Sistema Interbancario com Mensageria - Kafka

Criamos 3 Bancos predefinidos > BFA, STAND BANK, BIC

Tecnologias usadada ::

- Spring boot
- Apache kafka
- Postgresql

Problematica :::


O ecossistema de transações bancárias apresenta como desafio a implementação de
um sistema capaz de transacionar montantes em tempo real, as exigências do mercado
solicitam que as transações sejam efetuadas através do mesmo banco e para bancos
diferentes, e com um sistema de conciliação instantânea.
● Os diferentes engenheiros das diversas instituições financeiras, reuniram-se e optaram
pela implementação de um sistema que respeitasse a arquitetura apresentada na figura
abaixo indicado.
● É considerado também na inclusão de novos actores (bancos) do mercado ao longo do
tempo, e a sua inclusão neste ecossistema deverá sempre ser através do intermediário
bancário, principalmente para transações interbancárias.
● De modo a reduzir a carga de processamento nos serviços bancários, foi proposto a
implementação da arquitetura de microserviços, respeitando os padrões de projetos e
arquiteturais a serem utilizados para implementação dos processos de negócios de
suporte das transações mantidas pelos bancos. Tendo sempre em observância aos
princípios da norma ISO/IEC 25010:2011.
● É considerado também que os bancos possuem diferentes sistemas, que por sua vez,
possuem diferentes estruturas de dados para transacionar as suas operações
internamente na rede bancária. Deste modo, surge a necessidade de criar um
mecanismo que faça a tradução na comunicação entre as transações interbancárias,
dos bancos interligados neste ecossistema.
● Quando pretendido os clientes dos bancos desejam consultar o seu saldo assim como
o seu histórico de transações de débito ou créditos efectuadas em sua conta de acordo
com a identidade bancária informada. Podendo ser possível através do intermediário
financeiro e pelo próprio banco. Importa frisar que cada banco da rede gerencia a sua
própria rede de clientes que podem ser pessoas individuais ou colectivas. É
importante frisar também.

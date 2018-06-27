# Gumpu

Gumpu es una plataforma online de organización de compras en grupo. Se basa en la idea de que la compra por mayor resulta más barata pero no siempre se necesita la cantidad que requiere el mayorista.

## Funcionalidades
Actualmente la plataforma permite crear una cuenta la cual brinda al usuario distintas funcionalidades.
  - **Publicar productos**, indicando la cantidad necesaria para el pedido y el precio por unidad. Permite también agregar una imagen descriptiva y una lista de tags para mejorar la busqueda.
  - **Suscribirse a publicaciones**, indicando la cantidad que se desea comprar de un producto publicado.
  - **Buscar productos**, indicando las palabras claves a buscar.
  - **Poner en contacto**, para una publicación que ya alcanzó la cantidad necesaria, permite al supervisor enviar mensajes a cada suscriptor, y a los suscriptores, contactarse con el supervisor.
  - **Finalizar publicación**, cuando el supervisor realiza la compra (habiéndose contactado con los suscriptores), puede dar por finalizada la publicación. En este caso, de haber una puntuacion por parte del suscriptor o supervisor, esta se ve reflejada en la reputacion del usuario puntuado.
  - **Borrar publicación**, el supervisor de la publicación puede borrarla siempre y cuando esta no haya llegado a su objetivo. Si la publicación cuenta con suscriptores, se les da la posibilidad a éstos de volverse supervisores. Lo hará el primero que lo indique.
  - **Borrar suscripción**, siempre y cuando la publicación no haya llegado al objetivo, un usuario puede decidir no seguir suscripto.
  - **Aumentar la cantidad**, siempre que la publicación esté disponible, un suscriptor puede suscribirse nuevamente incrementando la cantidad que tenía previamente.

## Mails
Gumpu envía un mail de bienvenida al usuario que crea una cuenta. A su vez cuando una publicación alcanza su objectivo (está pendiente de pago), envía un mail a todos los suscriptores informando que ya pueden contactarse.

## Changelog
- **1.0** (Primera entrega):
  - Implementacion con JDBC en la capa de persistencia.

- **1.1** (Segunda entrega):
  - Uso de Hibernate en la capa de persistencia.
  - Se movio comportamiento de controllers a servicios.
  - Uso de transacciones.
  - Mails HTML con accion.
  - Correccion de bugs.
  - Correccion de estilos.
  - Revisión de la navegacion de profile.
  - Nuevas funcionalidades:
    - Se permite puntuar al supervisor y suscriptor.
    - Se muestra la calificacion (basada en las puntuaciones) en la lista de publicaciones.
    - Se permite enviar mensajes entre supervisor y suscriptor.
    - Se muestra en el area de mensajes los datos del suscriptor/supervisor.
    - Se permite agregar tags a las publicaciones. Estos se usan en la busqueda.

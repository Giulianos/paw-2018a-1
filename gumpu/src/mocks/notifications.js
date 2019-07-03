export const newMessage = {
  relatedMessage: {
    date: '2019-07-03T02:58:15.245-03:00',
    from: {
      email: 'gscaglioni@itba.edu.ar',
      id: 3,
      name: 'Giuliano ITBA',
    },
    id: 1,
    message: 'Hi supervisor!',
    seen: false,
    to: {
      email: 'scaglionigiuliano@gmail.com',
      id: 1,
      name: 'Giuliano',
    },
  },
  relatedOrder: {
    ordererId: 3,
    ordererUrl: 'http://pawserver.it.itba.edu.ar/paw-2018a-1/api/users/3',
    publicationId: 1,
    publicationUrl: 'http://pawserver.it.itba.edu.ar/paw-2018a-1/api/publications/1',
  },
  type: 'NEW_MESSAGES',
};

export const orderPurchased = {
  relatedOrder: {
    ordererId: 3,
    ordererUrl: 'http://pawserver.it.itba.edu.ar/paw-2018a-1/api/users/3',
    publicationId: 1,
    publicationUrl: 'http://pawserver.it.itba.edu.ar/paw-2018a-1/api/publications/1',
  },
  relatedPublication: {
    availableQuantity: 0,
    description: 'CocaCola',
    detailedDescription: 'Gaseosa CocaCola, botella de 1.5L por unidad',
    id: 1,
    imagesUrls: [],
    quantity: 10,
    status: 'PURCHASED',
    supervisorId: 1,
    supervisorUrl: 'http://pawserver.it.itba.edu.ar/paw-2018a-1/api/users/1',
    tags: [],
    unitPrice: 12,
  },
  type: 'ORDER_PURCHASED',
};

export const orderFulfilledSupervisor = {
  relatedPublication: {
    availableQuantity: 0,
    description: 'CocaCola',
    detailedDescription: 'Gaseosa CocaCola, botella de 1.5L por unidad',
    id: 1,
    imagesUrls: [],
    quantity: 10,
    status: 'FULFILLED',
    supervisorId: 1,
    supervisorUrl: 'http://pawserver.it.itba.edu.ar/paw-2018a-1/api/users/1',
    tags: [],
    unitPrice: 12,
  },
  type: 'PUBLICATION_FULFILLED',
};

export const orderFulfilledOrderer = {
  relatedOrder: {
    ordererId: 3,
    ordererUrl: 'http://pawserver.it.itba.edu.ar/paw-2018a-1/api/users/3',
    publicationId: 1,
    publicationUrl: 'http://pawserver.it.itba.edu.ar/paw-2018a-1/api/publications/1',
  },
  relatedPublication: {
    availableQuantity: 0,
    description: 'CocaCola',
    detailedDescription: 'Gaseosa CocaCola, botella de 1.5L por unidad',
    id: 1,
    imagesUrls: [],
    quantity: 10,
    status: 'FULFILLED',
    supervisorId: 1,
    supervisorUrl: 'http://pawserver.it.itba.edu.ar/paw-2018a-1/api/users/1',
    tags: [],
    unitPrice: 12,
  },
  type: 'PUBLICATION_FULFILLED',
};

export const supervisorLeftPublication = {
  relatedOrder: {
    ordererId: 3,
    ordererUrl: 'http://pawserver.it.itba.edu.ar/paw-2018a-1/api/users/3',
    publicationId: 1,
    publicationUrl: 'http://pawserver.it.itba.edu.ar/paw-2018a-1/api/publications/1',
  },
  relatedPublication: {
    availableQuantity: 2,
    description: 'CocaCola',
    detailedDescription: 'Gaseosa CocaCola, botella de 1.5L por unidad',
    id: 1,
    imagesUrls: [],
    quantity: 10,
    status: 'ORPHAN',
    tags: [],
    unitPrice: 12,
  },
  type: 'PUBLICATION_ORPHAN',
};

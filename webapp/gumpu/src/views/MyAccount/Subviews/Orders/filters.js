const filterByStatus = status => orders => orders.filter(o => o.publication.status === status);

export const getOrphan = filterByStatus('ORPHAN');
export const getFulfilled = filterByStatus('FULFILLED');
export const getInProgress = filterByStatus('IN_PROGRESS');
export const getPurchased = orders => filterByStatus('PURCHASED')(orders).filter(o => o.orderer.id !== o.publication.supervisorId);
const filterByStatus = status => publications => publications.filter(p => p.status === status);

export const getOrphan = filterByStatus('ORPHAN');
export const getFulfilled = filterByStatus('FULFILLED');
export const getInProgress = filterByStatus('IN_PROGRESS');
export const getPurchased = filterByStatus('PURCHASED');
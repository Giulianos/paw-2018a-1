import { createStore, combineReducers, applyMiddleware, compose } from 'redux';
import thunk from 'redux-thunk';

import sample from './sample/reducer';
import user from './user/reducer';

const reducer = combineReducers({
  user,
  sample
});

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

export const store = createStore(
  reducer,
  composeEnhancers(
    applyMiddleware(thunk)
  )
)
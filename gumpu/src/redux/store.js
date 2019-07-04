import {
  createStore, combineReducers, applyMiddleware, compose,
} from 'redux';
import thunk from 'redux-thunk';

import sample from './sample/reducer';
import auth from './auth/reducer';
import user from './user/reducer';
import publication from './publication/reducer';

const reducer = combineReducers({
  auth,
  user,
  publication,
  sample,
});

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

export const store = createStore(
  reducer,
  composeEnhancers(
    applyMiddleware(thunk),
  ),
);

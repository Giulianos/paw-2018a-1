import {
  createStore, combineReducers, applyMiddleware, compose,
} from 'redux';
import thunk from 'redux-thunk';

import sample from './sample/reducer';
import auth from './auth/reducer';
import images from './images/reducer';
import user from './user/reducer';
import publication from './publication/reducer';
import notifications from './notifications/reducer';

const reducer = combineReducers({
  auth,
  images,
  user,
  notifications,
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

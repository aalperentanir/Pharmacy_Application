import { thunk } from "redux-thunk";
import authReducer from "./Auth/Reducer";

import { combineReducers, legacy_createStore, applyMiddleware } from "redux";
import { orderReducer } from "./Order/Reducer";

import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import { customerReducer } from "./Customer/Reducer";
import { medicineReducer } from "./Medicine/Reducer";

const persistConfig = {
  key: 'root',
  storage,
};

const rootReducer = combineReducers({
    auth:authReducer,
    order:orderReducer,
    customer:customerReducer,
    medicine:medicineReducer

});

export const store = legacy_createStore(rootReducer, applyMiddleware(thunk));
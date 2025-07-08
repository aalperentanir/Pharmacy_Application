import { CREATE_ORDER_FAILURE, CREATE_ORDER_REQUEST, CREATE_ORDER_SUCCESS, DELETE_ORDER_FAILURE, DELETE_ORDER_REQUEST, DELETE_ORDER_SUCCESS, GET_ALL_ORDERS_FAILURE, GET_ALL_ORDERS_REQUEST, GET_ALL_ORDERS_SUCCESS, GET_CUSTOMER_ORDERS_FAILURE, GET_CUSTOMER_ORDERS_REQUEST, GET_CUSTOMER_ORDERS_SUCCESS, GET_ORDER_BY_ID_FAILURE, GET_ORDER_BY_ID_REQUEST, GET_ORDER_BY_ID_SUCCESS, UPDATE_ORDER_STATUS_FAILURE, UPDATE_ORDER_STATUS_REQUEST, UPDATE_ORDER_STATUS_SUCCESS } from "./ActionType"
  
  const initialState = {
    orders: [],
    order: null,
    loading: false,
    error: null,
  };
  
  export const orderReducer = (state = initialState, action) => {
    switch (action.type) {
      case CREATE_ORDER_REQUEST:
      case GET_ORDER_BY_ID_REQUEST:
      case DELETE_ORDER_REQUEST:
      case GET_ALL_ORDERS_REQUEST:
      case GET_CUSTOMER_ORDERS_REQUEST:
      case UPDATE_ORDER_STATUS_REQUEST:
        return {
          ...state,
          loading: true,
          error: null,
        };
  
      case CREATE_ORDER_SUCCESS:
        return {
          ...state,
          loading: false,
          order: action.payload,
          orders: [...state.orders, action.payload]
        };
  
      case GET_ALL_ORDERS_SUCCESS:
        return {
          ...state,
          loading: false,
          orders: action.payload,
        };
  
      case GET_ORDER_BY_ID_SUCCESS:
        return {
          ...state,
          loading: false,
          order: action.payload,
        };
  
      case DELETE_ORDER_SUCCESS:
        return {
          ...state,
          loading: false,
          orders: state.orders.filter((order) => order.id !== action.payload),
        };
  
      case GET_CUSTOMER_ORDERS_SUCCESS:
        return {
          ...state,
          loading: false,
          orders: action.payload,
        };
  
      case UPDATE_ORDER_STATUS_SUCCESS:
        return {
          ...state,
          loading: false,
          orders: state.orders.map((order) =>
            order.id === action.payload.id ? action.payload : order
          ),
          order: action.payload,
        };
  
      case CREATE_ORDER_FAILURE:
      case GET_ORDER_BY_ID_FAILURE:
      case DELETE_ORDER_FAILURE:
      case GET_CUSTOMER_ORDERS_FAILURE:
      case GET_ALL_ORDERS_FAILURE:
      case UPDATE_ORDER_STATUS_FAILURE:
        return {
          ...state,
          loading: false,
          error: action.error,
        };
  
      default:
        return state;
    }
  };
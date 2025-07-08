import {
  CREATE_CUSTOMER_FAILURE,
  CREATE_CUSTOMER_REQUEST,
  CREATE_CUSTOMER_SUCCESS,
  GET_ALL_CUSTOMERS_FAILURE,
  GET_ALL_CUSTOMERS_REQUEST,
  GET_ALL_CUSTOMERS_SUCCESS,
  GET_CUSTOMER_BY_ID_FAILURE,
  GET_CUSTOMER_BY_ID_REQUEST,
  GET_CUSTOMER_BY_ID_SUCCESS,
  GET_CUSTOMER_BY_ID_NUM_FAILURE,
  GET_CUSTOMER_BY_ID_NUM_REQUEST,
  GET_CUSTOMER_BY_ID_NUM_SUCCESS,
  GET_RECOMMENDED_PRODUCTS_FAILURE,
  GET_RECOMMENDED_PRODUCTS_REQUEST,
  GET_RECOMMENDED_PRODUCTS_SUCCESS,
  UPDATE_CUSTOMER_FAILURE,
  UPDATE_CUSTOMER_REQUEST,
  UPDATE_CUSTOMER_SUCCESS,
  DELETE_CUSTOMER_FAILURE,
  DELETE_CUSTOMER_REQUEST,
  DELETE_CUSTOMER_SUCCESS,
} from "./ActionType";

const initialState = {
    customers: [],
    currentCustomer: null,
    recommendedProducts:[],
    loading:false,
    error:null
}

export const customerReducer = (state = initialState, action) => {
    switch (action.type) {
        case CREATE_CUSTOMER_REQUEST:
        case GET_ALL_CUSTOMERS_REQUEST:
        case GET_CUSTOMER_BY_ID_REQUEST:
        case GET_CUSTOMER_BY_ID_NUM_REQUEST:
        case GET_RECOMMENDED_PRODUCTS_REQUEST:
        case UPDATE_CUSTOMER_REQUEST:
        case DELETE_CUSTOMER_REQUEST:
            return {...state,loading:true, error:null}

        case CREATE_CUSTOMER_SUCCESS:
            return {
                ...state,
                loading:false,
                customers: [...state.customers, action.payload],
                currentCustomer: action.payload
            }

        case GET_ALL_CUSTOMERS_SUCCESS:
            return {
                ...state,
                loading:false,
                customers:action.payload.data
            }

        case GET_CUSTOMER_BY_ID_SUCCESS:
        case GET_CUSTOMER_BY_ID_NUM_SUCCESS:
        case UPDATE_CUSTOMER_SUCCESS:
            return {
                ...state,
                loading:false,
                currentCustomer:action.payload.data
            }

        case GET_RECOMMENDED_PRODUCTS_SUCCESS:
            return {
                ...state,
                loading:false,
                recommendedProducts:action.payload.data
            }

        case DELETE_CUSTOMER_SUCCESS:
            return{
                ...state,
                loading:false,
                customers: state.customers.filter((customer)=> customer.id !== action.payload.id),
                currentCustomer:null
            }
        case CREATE_CUSTOMER_FAILURE:
        case GET_ALL_CUSTOMERS_FAILURE:
        case GET_CUSTOMER_BY_ID_FAILURE:
        case GET_CUSTOMER_BY_ID_NUM_FAILURE:
        case GET_RECOMMENDED_PRODUCTS_FAILURE:
        case UPDATE_CUSTOMER_FAILURE:
        case DELETE_CUSTOMER_FAILURE:
            return{
                ...state,
                loading:false,
                error:action.error
            }
        default:
            return state;
    }
}
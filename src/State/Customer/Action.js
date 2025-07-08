import api, { ADMIN_URL, BASE_URL } from "@/Config/api";
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

export const createCustomer = ({customerData, jwt}) => async(dispatch) => {
    dispatch({type:CREATE_CUSTOMER_REQUEST})

    try {
        const response = await api.post(`${BASE_URL}/customers`,customerData,{
            headers:{
                Authorization:`Bearer ${jwt}`
            }
        })

        dispatch({type:CREATE_CUSTOMER_SUCCESS, payload:response.data})
        console.log("CREATE CUSTOMER REQUEST: ",response.data);
    } catch (error) {
        dispatch({type:CREATE_CUSTOMER_FAILURE, payload:error.message})
        console.log("error",error);
        
    }
}

export const getAllCustomers = (jwt) => async(dispatch) => {
    dispatch({type:GET_ALL_CUSTOMERS_REQUEST})

    try {
        const response = await api.get(`${BASE_URL}/customers`,{
            headers:{
                Authorization:`Bearer ${jwt}`
            }
        })

        dispatch({type:GET_ALL_CUSTOMERS_SUCCESS, payload:response.data})
        console.log("GET ALL CUSTOMERS REQUEST: ",response.data);
    } catch (error) {
        dispatch({type:GET_ALL_CUSTOMERS_FAILURE, payload:error.message})
        console.log("error",error);
        
    }
}

export const getCustomerById = ({id,jwt}) => async(dispatch) => {
    dispatch({type:GET_CUSTOMER_BY_ID_REQUEST})

    try {
        const response = await api.get(`${BASE_URL}/customers/${id}`,{
            headers:{
                Authorization:`Bearer ${jwt}`
            }
        })

        dispatch({type:GET_CUSTOMER_BY_ID_SUCCESS, payload:response.data})
        console.log("GET CUSTOMER BY ID REQUEST: ",response.data);
    } catch (error) {
        dispatch({type:GET_CUSTOMER_BY_ID_FAILURE, payload:error.message})
        console.log("error",error);
        
    }
}

export const getCustomerByIdentityNumber = ({identityNumber,jwt}) => async (dispatch) => {
  dispatch({ type: GET_CUSTOMER_BY_ID_NUM_REQUEST });

  try {
    const response = await api.get(`${BASE_URL}/customers/idNum/${identityNumber}`,{
      headers:{
        Authorization:`Bearer ${jwt}`
      }
    });
    dispatch({ type: GET_CUSTOMER_BY_ID_NUM_SUCCESS, payload: response.data });
    console.log("getCustomerByIdentityNumber: ",response.data);
  } catch (error) {
    dispatch({ type: GET_CUSTOMER_BY_ID_NUM_FAILURE, error: error.message });
    console.log("error",error);
  }
};

export const getRecommendedProducts  = ({customerId,jwt}) => async (dispatch) => {
  dispatch({ type: GET_RECOMMENDED_PRODUCTS_REQUEST });

  try {
    const response = await api.get(`${BASE_URL}/customers/${customerId}/recommended-products`,{
      headers:{
        Authorization:`Bearer ${jwt}`
      }
    });
    dispatch({ type: GET_RECOMMENDED_PRODUCTS_SUCCESS, payload: response.data });
    console.log("getRecommendedProducts: ",response.data);
  } catch (error) {
    dispatch({ type: GET_RECOMMENDED_PRODUCTS_FAILURE, error: error.message });
    console.log("error",error);
  }
};

export const updateCustomer  = ({id,customerData,jwt}) => async (dispatch) => {
  dispatch({ type: UPDATE_CUSTOMER_REQUEST });

  try {
    const response = await api.put(`${ADMIN_URL}/admin/v0/customers/${id}`,customerData,{
        headers:{
            Authorization:`Bearer ${jwt}`
        }

    });
    dispatch({ type: UPDATE_CUSTOMER_SUCCESS, payload: response.data });
    console.log("updateCustomer: ",response.data);
  } catch (error) {
    dispatch({ type: UPDATE_CUSTOMER_FAILURE, error: error.message });
    console.log("error",error);
  }
};

export const deleteCustomer  = ({id,jwt}) => async (dispatch) => {
  dispatch({ type: DELETE_CUSTOMER_REQUEST });

  try {
    const response = await api.delete(`${ADMIN_URL}/admin/v0/customers/${id}`,{
        headers:{
            Authorization:`Bearer ${jwt}`
        }

    });
    dispatch({ type: DELETE_CUSTOMER_SUCCESS, payload: response.data });
    console.log("deleteCustomer: ",response.data);
  } catch (error) {
    dispatch({ type: DELETE_CUSTOMER_FAILURE, error: error.message });
    console.log("error",error);
  }
};
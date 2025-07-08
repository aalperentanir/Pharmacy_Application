import api, { BASE_URL } from "@/Config/api"
import { CREATE_ORDER_FAILURE, CREATE_ORDER_REQUEST, CREATE_ORDER_SUCCESS, DELETE_ORDER_FAILURE, DELETE_ORDER_REQUEST, DELETE_ORDER_SUCCESS, GET_ALL_ORDERS_FAILURE, GET_ALL_ORDERS_REQUEST, GET_ALL_ORDERS_SUCCESS, GET_CUSTOMER_ORDERS_FAILURE, GET_CUSTOMER_ORDERS_REQUEST, GET_CUSTOMER_ORDERS_SUCCESS, GET_ORDER_BY_ID_FAILURE, GET_ORDER_BY_ID_REQUEST, GET_ORDER_BY_ID_SUCCESS, UPDATE_ORDER_STATUS_FAILURE, UPDATE_ORDER_STATUS_REQUEST, UPDATE_ORDER_STATUS_SUCCESS } from "./ActionType"

export const createOrder = ({orderData,jwt}) => async(dispatch) =>{

    dispatch({type:CREATE_ORDER_REQUEST})

    try {
        const response = await api.post(`${BASE_URL}/orders`,orderData,{
            headers:{
                Authorization:`Bearer ${jwt}`
            }
        });

        dispatch({type:CREATE_ORDER_SUCCESS, payload:response.data})
        console.log("CREATE ORDER REQUEST SUCCESS",response.data)
    } catch (error) {
        console.log("error",error);
        dispatch({type:CREATE_ORDER_FAILURE, error:error.message})
        
    }

}

export const getAllOrders = (jwt) => async(dispatch) => {
    dispatch({type:GET_ALL_ORDERS_REQUEST})

    try {
        const response = await api.get(`${BASE_URL}/orders`,{
            headers:{
                Authorization:`Bearer ${jwt}`
            }
        });
        dispatch({type:GET_ALL_ORDERS_SUCCESS, payload:response.data})
        console.log("GET ALL ORDERS REQUEST SUCCESS ",response.data)
    } catch (error) {
        console.log(error);
        dispatch({type:GET_ALL_ORDERS_FAILURE, error:error.message})
    }
}

export const getOrderById = ({id,jwt}) => async(dispatch) => {
    dispatch({type:GET_ORDER_BY_ID_REQUEST})

    try {
        const response = await api.get(`${BASE_URL}/orders/${id}`,{
            headers:{
                Authorization:`Bearer ${jwt}`
            }
        });
        dispatch({type:GET_ORDER_BY_ID_SUCCESS, payload:response.data})
        console.log("GET ORDER BY ID REQUEST SUCCESS ",response.data)
    } catch (error) {
        console.log(error);
        dispatch({type:GET_ORDER_BY_ID_FAILURE, error:error.message})
    }
}

export const deleteOrder = ({id,jwt}) => async(dispatch) => {
    dispatch({type:DELETE_ORDER_REQUEST})

    try {
        const response = await api.delete(`${BASE_URL}/orders/${id}`,{
            headers:{
                Authorization:`Bearer ${jwt}`
            }
        });
        dispatch({type:DELETE_ORDER_SUCCESS, payload:id})
        console.log("DELETE ORDER REQUEST SUCCESS ",response.data)
    } catch (error) {
        console.log(error);
        dispatch({type:DELETE_ORDER_FAILURE, error:error.message})
    }
}

export const getCustomerOrders = ({customerId,jwt}) => async(dispatch) => {
    dispatch({type:GET_CUSTOMER_ORDERS_REQUEST})

    try {
        const response = await api.get(`${BASE_URL}/orders/customer/${customerId}`,{
            headers:{
                Authorization:`Bearer ${jwt}`
            }
        });
        dispatch({type:GET_CUSTOMER_ORDERS_SUCCESS, payload:response.data})
        console.log("GET CUSTOMER ORDERS REQUEST SUCCESS ",response.data)
    } catch (error) {
        console.log(error);
        dispatch({type:GET_CUSTOMER_ORDERS_FAILURE, error:error.message})
    }
}

export const updateOrderStatus = ({orderId, orderStatus, jwt}) => async(dispatch) => {
    dispatch({type: UPDATE_ORDER_STATUS_REQUEST});

    try {
        const response = await api.patch(
            `${BASE_URL}/orders/${orderId}/status`,
            null,
            {
                headers: {
                    Authorization: `Bearer ${jwt}`
                },
                params: {
                    orderStatus
                }
            }
        );
        
        dispatch({
            type: UPDATE_ORDER_STATUS_SUCCESS, 
            payload: response.data
        });
        console.log("Order status updated:", response.data);
    } catch (error) {
        console.error("Update status error:", error);
        dispatch({
            type: UPDATE_ORDER_STATUS_FAILURE, 
            error: error.response?.data?.message || error.message
        });
    }
}
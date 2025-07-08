// CustomerOrders.jsx
import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";
import { ArrowLeftIcon } from "@radix-ui/react-icons";
import { Trash2 } from "lucide-react";
import { useDispatch, useSelector } from "react-redux";
import { getAllOrders, updateOrderStatus, deleteOrder } from "@/State/Order/Action";

export const Order = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { order } = useSelector(store => store);
  const jwt = localStorage.getItem("jwt");

  useEffect(() => {
    if (jwt) {
      dispatch(getAllOrders(jwt));
    }
  }, [dispatch, jwt]);

  const handleStatusChange = (orderId, newStatus) => {
    if (jwt) {
        dispatch(updateOrderStatus({ 
            orderId, 
            orderStatus: newStatus,
            jwt 
        }));
    }
};

  const handleRemoveOrder = (orderId) => {
    if (jwt && window.confirm("Are you sure you want to delete this order?")) {
      dispatch(deleteOrder({ id:orderId, jwt }));
    }
  };

  const formatDate = (dateString) => {
    const options = { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' };
    return new Date(dateString).toLocaleDateString(undefined, options);
  };

  return (
    <div className="p-5 lg:px-20">
      <div className="flex items-center gap-4 pb-5">
        <Button
          variant="outline"
          size="icon"
          onClick={() => navigate(-1)}
          className="h-8 w-8"
        >
          <ArrowLeftIcon className="h-4 w-4" />
        </Button>
        <h1 className="font-bold text-3xl">Orders</h1>
      </div>

      {order.loading ? (
        <div className="text-center py-10">Loading orders...</div>
      ) : order.error ? (
        <div className="text-center py-10 text-red-500">Error: {order.error}</div>
      ) : (
        <Table className="border">
          <TableHeader>
            <TableRow>
              <TableHead className="py-5">Order ID</TableHead>
              <TableHead>Medicines</TableHead>
              <TableHead>Customer</TableHead>
              <TableHead>Quantity</TableHead>
              <TableHead>Total</TableHead>
              <TableHead>Date</TableHead>
              <TableHead>Status</TableHead>
              <TableHead className="text-right">Action</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {order.orders?.length > 0 ? (
              order.orders.map((orderItem) => (
                <TableRow key={orderItem.id}>
                  <TableCell>{orderItem.id}</TableCell>
                  <TableCell>
                    {orderItem.items?.map(item => (
                      <div key={item.id} className="mb-1">
                        {item.medicineBrand}
                      </div>
                    ))}
                  </TableCell>
                  <TableCell>{orderItem.customerName}</TableCell>
                  <TableCell>
                    {orderItem.items?.reduce((sum, item) => sum + item.quantity, 0)}
                  </TableCell>
                  <TableCell>{orderItem.totalPrice} TL</TableCell>
                  <TableCell>{formatDate(orderItem.purchaseDate)}</TableCell>
                  <TableCell>
                    <select
                      value={orderItem.status}
                      onChange={(e) => handleStatusChange(orderItem.id, e.target.value)}
                      className="border rounded px-2 py-1 bg-background"
                    >
                      <option value="PENDING">PENDING</option>
                      <option value="COMPLETED">COMPLETED</option>
                      <option value="CANCELLED">CANCELLED</option>
                    </select>
                  </TableCell>
                  <TableCell className="text-right">
                    <Button
                      variant="ghost"
                      onClick={() => handleRemoveOrder(orderItem.id)}
                      size="icon"
                      className="h-8 w-8 text-red-600 hover:text-red-800"
                    >
                      <Trash2 className="w-4 h-4" />
                    </Button>
                  </TableCell>
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={8} className="text-center py-5">
                  No orders found
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      )}
    </div>
  );
};
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useDispatch } from "react-redux";
import { createOrder } from "@/State/Order/Action";

const CreateOrder = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const jwt = localStorage.getItem("jwt");

  const [order, setOrder] = useState({
    customerId: "",
    medicineId: "",
    quantity: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setOrder((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const formattedOrder = {
      customerId: parseInt(order.customerId),
      totalPrice: parseFloat(order.totalPrice),
      items: [
        {
          medicineId: parseInt(order.medicineId),
          quantity: parseInt(order.quantity),
        },
      ],
    };

    dispatch(createOrder({ orderData: formattedOrder, jwt }));
    console.log("New order:", formattedOrder);
    navigate("/orders");
  };

  return (
    <div className="p-5 lg:px-20 max-w-4xl mx-auto">
      <h1 className="font-bold text-3xl pb-5">Create Order</h1>

      <form onSubmit={handleSubmit} className="space-y-4">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <Label htmlFor="customerId">Customer Id</Label>
            <Input
              id="customerId"
              name="customerId"
              value={order.customerId}
              onChange={handleChange}
              required
              placeholder="Customer Id"
            />
          </div>

          <div>
            <Label htmlFor="medicineId">Medicine Id</Label>
            <Input
              id="medicineId"
              name="medicineId"
              value={order.medicineId}
              onChange={handleChange}
              required
              placeholder="Medicine Id"
            />
          </div>

          <div>
            <Label htmlFor="quantity">Quantity</Label>
            <Input
              id="quantity"
              name="quantity"
              value={order.quantity}
              onChange={handleChange}
              required
              placeholder="Quantity"
            />
          </div>
        </div>

        <div className="flex justify-end space-x-2 pt-4">
          <Button
            type="button"
            variant="outline"
            onClick={() => navigate("/orders")}
          >
            Cancel
          </Button>
          <Button type="submit">Create Order</Button>
        </div>
      </form>
    </div>
  );
};

export default CreateOrder;

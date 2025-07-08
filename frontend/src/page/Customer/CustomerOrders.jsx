import React, { useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { ArrowLeftIcon } from "@radix-ui/react-icons";
import { useDispatch, useSelector } from "react-redux";
import { getCustomerOrders } from "@/State/Order/Action";

export const CustomerOrders = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const jwt = localStorage.getItem("jwt");

  const { order } = useSelector((store) => store);

  console.log("order", order);

  useEffect(() => {
    if (jwt) {
      dispatch(getCustomerOrders({ customerId: id, jwt }));
    }
  }, [dispatch, jwt]);

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
        <h1 className="font-bold text-3xl">Customer Orders</h1>
      </div>

      <Table className="border">
        <TableHeader>
          <TableRow>
            <TableHead className="py-5">Order ID</TableHead>
            <TableHead>Medicine</TableHead>
            <TableHead>Quantity</TableHead>
            <TableHead>Total Price</TableHead>
            <TableHead>Date</TableHead>
            <TableHead>Status</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {order.orders.map((order) =>
            order.items.map((item, index) => (
              <TableRow key={`${order.id}-${index}`}>
                <TableCell>{order.id}</TableCell>
                <TableCell>{item.medicineBrand}</TableCell>
                <TableCell>{item.quantity}</TableCell>
                <TableCell>{order.totalPrice} TL</TableCell>
                <TableCell>
                  {" "}
                  {new Date(order.purchaseDate).toLocaleString("tr-TR", {
                    day: "2-digit",
                    month: "long",
                    year: "numeric",
                    hour: "2-digit",
                    minute: "2-digit",
                  })}
                </TableCell>
                <TableCell>
                  <Badge
                    variant={
                      order.status === "COMPLETED" ? "default" : "secondary"
                    }
                  >
                    {order.status}
                  </Badge>
                </TableCell>
              </TableRow>
            ))
          )}
        </TableBody>
      </Table>
    </div>
  );
};

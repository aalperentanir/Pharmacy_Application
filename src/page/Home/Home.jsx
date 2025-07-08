import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardHeader,
  CardTitle,
  CardDescription,
  CardContent,
} from "@/components/ui/card";
import {
  LayoutDashboard,
  Pill,
  Users,
  PlusCircle,
  ListOrdered,
} from "lucide-react";
import { useDispatch, useSelector } from "react-redux";
import { getAllCustomers } from "@/State/Customer/Action";
import { getAllOrders } from "@/State/Order/Action";
import { getAllMedicines } from "@/State/Medicine/Action";

const Home = () => {
  const navigate = useNavigate();
  const jwt = localStorage.getItem("jwt");
  const dispatch = useDispatch();
  const customers = useSelector((state) => state.customer.customers);
  const orders = useSelector(state => state.order.orders)
  const medicines = useSelector(state => state.medicine.medicines)
  const totalRevenue = orders.reduce((sum, order) => sum + order.totalPrice, 0);
  const {auth} = useSelector(store => store)

  const userRole = auth.user.role
  console.log("medicines",medicines)

  useEffect(() => {
    if (jwt) {
      dispatch(getAllCustomers(jwt));
    }
  }, [dispatch, jwt]);

    useEffect(() => {
    if (jwt) {
      dispatch(getAllOrders(jwt));
    }
  }, [dispatch, jwt]);

  
    useEffect(() => {
    if (jwt) {
      dispatch(getAllMedicines(jwt));
    }
  }, [dispatch, jwt]);
  const quickActions = [
    {
      title: "Manage Medicines",
      icon: <Pill className="h-6 w-6" />,
      description: "View and edit medicine inventory",
      action: () => navigate("/medicines"),
    },
    {
      title: "Manage Customers",
      icon: <Users className="h-6 w-6" />,
      description: "View and manage customer records",
      action: () => navigate("/customers"),
    },
    {
      title: "Add New Medicine",
      icon: <PlusCircle className="h-6 w-6" />,
      description: "Add new medicine to inventory",
      action: () => navigate("/create-medicine"),
    },
    {
      title: "View Orders",
      icon: <ListOrdered className="h-6 w-6" />,
      description: "Check all customer orders",
      action: () => navigate("/orders"),
    },
  ];

  const stats = [
    {
      name: "Total Medicines",
      value: medicines.length,
      change: "+12%",
      changeType: "positive",
    },
    {
      name: "Total Customers",
      value: customers.length,
      change: "+5%",
      changeType: "positive",
    },
    {
      name: "Pending Orders",
      value: orders.length,
      change: "-2%",
      changeType: "negative",
    },
    {
      name: "Revenue",
      value: totalRevenue + " TL",
      change: "+18%",
      changeType: "positive",
    },
  ];

  return (
    <div className="p-5 lg:px-20">
      <div className="pb-8">
        <h1 className="text-3xl font-bold tracking-tight">
          Pharmacy {userRole} Dashboard
        </h1>
        <p className="text-muted-foreground mt-2">
          Welcome back! Here's what's happening with your pharmacy today.
        </p>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-8">
        {stats.map((stat, index) => (
          <Card key={index}>
            <CardHeader className="pb-2">
              <CardDescription className="text-sm font-medium">
                {stat.name}
              </CardDescription>
              <CardTitle className="text-2xl font-bold">{stat.value}</CardTitle>
            </CardHeader>
            <CardContent>
              <div
                className={`text-xs ${
                  stat.changeType === "positive"
                    ? "text-green-500"
                    : "text-red-500"
                }`}
              >
                {stat.change} from last month
              </div>
            </CardContent>
          </Card>
        ))}
      </div>
      <div className="mb-8">
        <h2 className="text-xl font-semibold mb-4">Quick Actions</h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          {quickActions.map((action, index) => (
            <Card
              key={index}
              className="hover:shadow-md transition-shadow cursor-pointer"
              onClick={action.action}
            >
              <CardHeader>
                <div className="flex items-center space-x-4">
                  <div className="p-2 rounded-lg bg-primary/10">
                    {action.icon}
                  </div>
                  <div>
                    <CardTitle className="text-lg">{action.title}</CardTitle>
                    <CardDescription>{action.description}</CardDescription>
                  </div>
                </div>
              </CardHeader>
            </Card>
          ))}
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <Card>
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <Pill className="h-5 w-5" />
              <span>Recently Added Medicines</span>
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {medicines.map((item) => (
                <div
                  key={item}
                  className="flex items-center justify-between p-2 hover:bg-gray-50 rounded"
                >
                  <div>
                    <p className="font-medium">{item.brand}</p>
                    <p className="text-sm text-muted-foreground">
                      Added 2 days ago
                    </p>
                  </div>
                  <Button
                    variant="outline"
                    size="sm"
                    onClick={() => navigate(`/medicines/${item.id}`)}
                  >
                    View
                  </Button>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <Users className="h-5 w-5" />
              <span>Recent Customers</span>
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {customers && customers.length > 0 ? (
                customers.map((item) => (
                  <div
                    key={item.id}
                    className="flex items-center justify-between p-2 hover:bg-gray-50 rounded"
                  >
                    <div>
                      <p className="font-medium">
                        {item.firstName} {item.lastName}
                      </p>
                      <p className="text-sm text-muted-foreground">
                        Joined {item.joinDate || "N/A"}
                      </p>
                    </div>
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={() => navigate(`/customers/${item.id}`)}
                    >
                      View
                    </Button>
                  </div>
                ))
              ) : (
                <p>No customers found</p>
              )}
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default Home;

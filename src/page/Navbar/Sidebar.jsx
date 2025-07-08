import { Button } from "@/components/ui/button";
import { SheetClose } from "@/components/ui/sheet";
import {
  ExitIcon,
  HomeIcon,
  PersonIcon,
} from "@radix-ui/react-icons";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import VaccinesIcon from '@mui/icons-material/Vaccines';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import Face2Icon from '@mui/icons-material/Face2';
import { ClipboardList, ShoppingCart, FileText, Package, ListOrdered,ClipboardPlus } from "lucide-react";
import { Pill, Users, Settings } from "lucide-react";
import { logout } from "@/State/Auth/Action";
const Sidebar = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const {auth} = useSelector(store => store)

  const userRole = auth.user.role




  const managerOnlyMenu = [
    { name: "Home", path: "/", icon: <HomeIcon className="h-6 w-6" /> },

    {
      name: "Medicines",
      path: "/medicines",
      icon: <Pill className="h-6 w-6" />,
    },
    {
      name: "Create Medicine",
      path: "/create-medicine",
      icon: <VaccinesIcon className="h-6 w-6" />,
    },
    {
      name: "Customers",
      path: "/customers",
      icon: <Face2Icon className="h-6 w-6" />,
    },
    {
      name: "Create Customer",
      path: "/create-customer",
      icon: <PersonAddIcon className="h-6 w-6" />,
    },
    {
      name: "Orders",
      path: "/orders",
      icon: <ClipboardList  className="h-6 w-6" />,
    },
    {
      name: "Create Order",
      path: "/create-order",
      icon: <ClipboardPlus className="h-6 w-6" />,
    },
    {
      name: "Profile",
      path: "/profile",
      icon: <PersonIcon className="h-6 w-6" />,
    },
    { name: "Logout", path: "/", icon: <ExitIcon className="h-6 w-6" /> },

  ];

  const employeeMenu = [
    { name: "Home", path: "/", icon: <HomeIcon className="h-6 w-6" /> },
    {
      name: "Medicines",
      path: "/medicines",
      icon: <VaccinesIcon className="h-6 w-6" />,
    },
    {
      name: "Customers",
      path: "/customers",
      icon: <Face2Icon className="h-6 w-6" />,
    },
    {
      name: "Create Customer",
      path: "/create-customer",
      icon: <PersonAddIcon className="h-6 w-6" />,
    },
    {
      name: "Orders",
      path: "/orders",
      icon: <ClipboardList  className="h-6 w-6" />,
    },
    {
      name: "Create Order",
      path: "/create-order",
      icon: <ClipboardPlus className="h-6 w-6" />,
    },
    {
      name: "Profile",
      path: "/profile",
      icon: <PersonIcon className="h-6 w-6" />,
    },
    { name: "Logout", path: "/", icon: <ExitIcon className="h-6 w-6" /> },
  ];



  const filteredMenu = userRole === 'MANAGER' 
    ? [...managerOnlyMenu] 
    : employeeMenu;

  const handleLogout = () => {
    dispatch(logout())
  };

  return (
    <div className="mt-10 space-y-5">
      {filteredMenu.map((item) => (
        <div key={item.name}>
          <SheetClose className="w-full">
            <Button
              className="flex items-center gap-5 py-6 w-full"
              onClick={() => {
                navigate(item.path);
                if (item.name === "Logout") {
                  handleLogout();
                }
              }}
            >
              <span className="w-8">{item.icon}</span>
              <p>{item.name}</p>
            </Button>
          </SheetClose>
        </div>
      ))}
    </div>
  );
};

export default Sidebar;
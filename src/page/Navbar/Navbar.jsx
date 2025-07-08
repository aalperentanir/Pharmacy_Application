import React, { useState } from "react";
import {
  Sheet,
  SheetContent,
  SheetDescription,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui/sheet";
import { Button } from "@/components/ui/button";
import {
  DragHandleHorizontalIcon,
  MagnifyingGlassIcon,
} from "@radix-ui/react-icons";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import Sidebar from "./Sidebar";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";

const Navbar = () => {
  const navigate = useNavigate();
  const [showSearch, setShowSearch] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const {auth} = useSelector(store=> store)

  const handleSearch = () => {
    console.log("Searching for:", searchTerm);
    navigate(`/search?query=${searchTerm}`);
  };

  return (
    <div className="px-2 py-3 border-b z-50 bg-background bg-opacity-0 sticky top-0 left-0 right-0 flex justify-between items-center">
      <div className="flex items-center gap-3">
        <Sheet>
          <SheetTrigger>
            <Button
              variant="ghost"
              size="icon"
              className="rounded-full h-11 w-11"
            >
              <DragHandleHorizontalIcon className="h-7 w-7" />
            </Button>
          </SheetTrigger>
          <SheetContent
            className="w-72 border-r-0 flex flex-col justify-center"
            side="left"
          >
            <SheetHeader>
              <SheetTitle>
                <div className="text-3xl flex justify-center items-center gap-1">
                  <Avatar>
                    <AvatarImage />
                  </Avatar>

                  <div>
                    <span className="font-bold text-blue-700">
                      Pharmacy Application
                    </span>
                  </div>
                </div>
              </SheetTitle>
            </SheetHeader>
            <Sidebar />
          </SheetContent>
        </Sheet>
        <p
          className="text-sm lg:text-base cursor-pointer"
          onClick={() => navigate("/")}
        >
          Pharmacy Application
        </p>
        <div className="p-0 ml-9 relative">
          {!showSearch ? (
            <Button
              variant="outline"
              className="flex items-center gap-3"
              onClick={() => setShowSearch(true)}
            >
              <MagnifyingGlassIcon />
              <span>Search</span>
            </Button>
          ) : (
            <div className="flex gap-2">
              <input
                type="text"
                className="border rounded px-2 py-1"
                placeholder="Search..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
              <Button onClick={handleSearch}>Go</Button>
              <Button variant="ghost" onClick={() => setShowSearch(false)}>
                X
              </Button>
            </div>
          )}
        </div>
      </div>
      <div>
        <Avatar>
          <AvatarFallback>{auth.user?.firstName[0].toUpperCase()}</AvatarFallback>
        </Avatar>
      </div>
    </div>
  );
};

export default Navbar;

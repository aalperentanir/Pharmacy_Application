import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import React from "react";
import { useSelector } from "react-redux";

const Profile = () => {
  const {auth} = useSelector(store=> store)
  console.log("auth",auth)
  return (
    <div className="flex flex-col items-center mb-5">
      <div className="pt-10 w-full lg:w-[60%]">
        <Card>
          <CardHeader className="pb-9">
            <CardTitle>User Information</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="lg:flex gap-32">
              <div className="space-y-7">
                <div className="flex">
                  <p className="w-[9rem]">First Name: </p>
                  <p className="text-gray-400">{auth.user?.firstName}</p>
                </div>

                <div className="flex">
                  <p className="w-[9rem]">Last Name: </p>
                  <p className="text-gray-400">{auth.user?.lastName}</p>
                </div>

                <div className="flex">
                  <p className="w-[9rem]">Email: </p>
                  <p className="text-gray-400">{auth.user?.email}</p>
                </div>

                <div className="flex">
                  <p className="w-[9rem]">Role: </p>
                  <p className="text-gray-400">{auth.user?.role}</p>
                </div>
              </div>

            </div>
          </CardContent>
        </Card>

      </div>
    </div>
  );
};

export default Profile;

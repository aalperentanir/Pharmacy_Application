import React from "react";

import "./Auth.css";
import SignupForm from "./SignupForm";
import SigninForm from "./SigninForm"
import { Button } from "@/components/ui/button";
import { useLocation, useNavigate } from "react-router-dom";
const Auth = () => {
  const navigate = useNavigate();

  const location = useLocation();

  return (
    <div className="h-screen relative authContainer">
      <div className="absolute top-0 right-0 left-0 bottom-0 bg-[#30712] bg-opacity-50">
        <div
          className="bgBlure absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 flex flex-col justify-center 
        items-center h-[35rem] w-[30rem] rounded-md z-50 bg-black bg-opacity-50 shadow-2xl shadow-white text-white px-10"
        >
          <h1 className="text-4xl font-bold pb-3">Pharmacy Application</h1>

          {location.pathname == "/signup" ? (
            <section className="w-full">
              <SignupForm />
              <div className="flex items-center justify-center">
                <span className="">Already have an account?</span>
                <Button onClick={() => navigate("/signin")} variant="link">
                  Signin
                </Button>
              </div>
            </section>
          ) : location.pathname == "/forget-password" ? (
            <section className="w-full">
              <div className="flex items-center justify-center mt-2">
                <span className="">Back to login</span>
                <Button onClick={() => navigate("/signin")} variant="link">
                  Signin
                </Button>
              </div>
            </section>
          ) : (
            <section className="w-full">
              <SigninForm />
              <div className="flex items-center justify-center">
                <span className="">Create your account ?</span>
                <Button onClick={() => navigate("/signup")} variant="link">
                  Signup
                </Button>
              </div>
            </section>
          )}
        </div>
      </div>
    </div>
  );
};

export default Auth;

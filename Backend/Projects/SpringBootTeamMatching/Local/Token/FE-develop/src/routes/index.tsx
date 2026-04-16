import { createBrowserRouter } from "react-router-dom";
import { publicRoutes } from "./public-routes";
import { protectedRoutes } from "./protected-routes";
import NotFound from "../pages/member";
import MobileLayout from "../layouts/mobileLayout";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <MobileLayout />,
    children: [
      ...publicRoutes,

      {
        // element: <ProtectedRoute />,
        children: protectedRoutes,
      },
      {
        path: "*",
        element: <NotFound />,
      },
    ],
  },
]);
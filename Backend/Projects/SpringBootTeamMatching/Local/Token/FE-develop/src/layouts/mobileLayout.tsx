import { Outlet, useLocation, useNavigate } from "react-router-dom";
import Home from "../assets/footer/home.svg?react";
import Member from "../assets/footer/member.svg?react";
import Project from "../assets/footer/project.svg?react";
import Board from "../assets/footer/board.svg?react";
import My from "../assets/footer/my.svg?react";
import { PATH } from "../components/path";

export default function MobileLayout() {
  const location = useLocation();
  const navigate = useNavigate();

  const hideFooterPaths = [PATH.ONBOARDING, PATH.LOGIN, PATH.SIGNUP];
  const hideFooter = hideFooterPaths.some((path) => path === location.pathname);

  const footerMenu = [
    { label: "홈", path: PATH.HOME, icon: Home },
    { label: "팀원", path: PATH.MEMBER, icon: Member },
    { label: "프로젝트", path: PATH.PROJECTS, icon: Project },
    { label: "게시판", path: PATH.BOARD, icon: Board },
    { label: "마이", path: PATH.MY, icon: My },
  ];

  return (
    <div className="flex min-h-[100dvh] justify-center bg-white shadow-2xl">
      <div className="relative w-full max-w-[400px] bg-white shadow-md">
        <Outlet />

        {!hideFooter && (
          <div className="fixed bottom-0 flex h-16 w-full max-w-[400px] border-t border-black bg-[#FFFFFF]">
            {footerMenu.map((menu) => {
              const Icon = menu.icon;

              const isActive =
                menu.path === PATH.MY
                  ? location.pathname.startsWith(PATH.MY)
                  : location.pathname === menu.path;

              return (
                <button
                  key={menu.label}
                  className="flex flex-1 cursor-pointer items-center justify-center border-0 p-0"
                  onClick={() => navigate(menu.path)}
                >
                  <div className="flex flex-col items-center gap-[2px]">
                    <Icon
                      className={`size-5 fill-none stroke-2 ${
                        isActive ? "text-[#155DFC]" : "text-[#90A1B9]"
                      }`}
                    />
                    <p
                      className={`text-[10px] font-bold ${
                        isActive ? "text-[#155DFC]" : "text-[#90A1B9]"
                      }`}
                    >
                      {menu.label}
                    </p>
                  </div>
                </button>
              );
            })}
          </div>
        )}
      </div>
    </div>
  );
}
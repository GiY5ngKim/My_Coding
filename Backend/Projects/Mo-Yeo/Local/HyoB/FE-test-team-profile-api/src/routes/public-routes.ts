import { lazy } from 'react';
import { PATH } from '../components/path';

const OnboardingPage = lazy(() => import('../pages/onBoarding'));
const LoginPage = lazy(() => import('../pages/login'));
const OAuthCallback = lazy(() => import('../pages/OAuthCallback'));

export const publicRoutes = [
  { path: PATH.ONBOARDING, Component: OnboardingPage },
  { path: PATH.LOGIN, Component: LoginPage },
  { path: "/oauth/callback", Component: OAuthCallback },
];
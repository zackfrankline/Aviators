import { Article } from '../types/content.types';

export type ArticleStatus = 'Open' | 'Working' | 'Published';


const generateSlug = (title: string): string =>
  title
    .toLowerCase()
    .replace(/[^a-z0-9\s-]/g, '')
    .trim()
    .replace(/\s+/g, '-');

const generate200WordContent = (topic: string): string => {
  return `Exploring the intricacies of ${topic} requires a structured understanding of both foundational concepts and modern implementation strategies. In today's rapidly evolving technical landscape, teams frequently encounter bottlenecks when scaling systems or adopting unfamiliar paradigms without proper architectural foresight. By establishing clear boundaries, maintaining strict type safety, and prioritizing observability from day one, engineers can significantly reduce long-term maintenance overhead. Furthermore, continuous iteration paired with automated testing pipelines ensures that incremental updates do not introduce regressions into production environments. Collaboration across cross-functional squads also plays a vital role; when product designers, backend developers, and infrastructure specialists align on shared metrics, delivery velocity improves naturally. Another critical aspect involves resource optimization—balancing latency, throughput, and operational costs to match real-world usage patterns rather than premature assumptions. Documentation should remain living and accessible, empowering new contributors to onboard swiftly and make meaningful commits within their first week. Ultimately, mastering ${topic} is less about chasing every emerging trend and more about applying proven engineering principles consistently. As organizations mature, those that invest in resilient workflows, thoughtful abstraction layers, and iterative feedback loops will consistently outperform competitors while keeping technical debt firmly under control across every release cycle.`;
};

const rawArticles: {
  title: string;
  summary: string;
  status: ArticleStatus;
  categoryId: string;
  authorId: string;
  bannerUrl: string | null;
  youtubeLink: string | null;
  pdfUrl: string | null;
  daysAgo: number;
}[] = [
  {
    title: 'Getting Started with TypeScript Generics',
    summary: 'Learn how to write reusable, type-safe functions and interfaces using TypeScript generics.',
    status: 'Published',
    categoryId: 'cat-typescript',
    authorId: 'auth-01',
    bannerUrl: 'https://cdn.example.com/banners/ts-generics.jpg',
    youtubeLink: 'https://youtube.com/watch?v=ts-generics-101',
    pdfUrl: 'https://cdn.example.com/pdfs/ts-generics-guide.pdf',
    daysAgo: 45,
  },
  {
    title: 'Scaling Node Microservices in Production',
    summary: 'Architectural patterns for load balancing, service discovery, and fault tolerance in Node.js.',
    status: 'Published',
    categoryId: 'cat-backend',
    authorId: 'auth-02',
    bannerUrl: 'https://cdn.example.com/banners/node-microservices.jpg',
    youtubeLink: null,
    pdfUrl: 'https://cdn.example.com/pdfs/node-scaling.pdf',
    daysAgo: 40,
  },
  {
    title: 'Understanding React Server Components',
    summary: 'A deep dive into zero-bundle-size components and server-first rendering workflows.',
    status: 'Working',
    categoryId: 'cat-frontend',
    authorId: 'auth-01',
    bannerUrl: 'https://cdn.example.com/banners/rsc-guide.jpg',
    youtubeLink: 'https://youtube.com/watch?v=rsc-explained',
    pdfUrl: null,
    daysAgo: 38,
  },
  {
    title: 'PostgreSQL Indexing Strategies for High Throughput',
    summary: 'How to choose between B-Tree, GIN, GiST, and BRIN indexes for complex queries.',
    status: 'Published',
    categoryId: 'cat-database',
    authorId: 'auth-03',
    bannerUrl: 'https://cdn.example.com/banners/pg-indexes.jpg',
    youtubeLink: null,
    pdfUrl: 'https://cdn.example.com/pdfs/postgres-indexing.pdf',
    daysAgo: 35,
  },
  {
    title: 'Zero Downtime Database Migrations',
    summary: 'Step-by-step expand-and-contract patterns for evolving relational schemas safely.',
    status: 'Open',
    categoryId: 'cat-database',
    authorId: 'auth-03',
    bannerUrl: null,
    youtubeLink: null,
    pdfUrl: null,
    daysAgo: 32,
  },
  {
    title: 'Building Accessible Design Systems',
    summary: 'Practical WCAG compliance checklists, keyboard navigation patterns, and ARIA best practices.',
    status: 'Published',
    categoryId: 'cat-uiux',
    authorId: 'auth-04',
    bannerUrl: 'https://cdn.example.com/banners/a11y-design.jpg',
    youtubeLink: 'https://youtube.com/watch?v=a11y-systems',
    pdfUrl: null,
    daysAgo: 30,
  },
  {
    title: 'Introduction to Distributed Tracing with OpenTelemetry',
    summary: 'Instrumenting services to visualize request lifecycles across microservice boundaries.',
    status: 'Working',
    categoryId: 'cat-devops',
    authorId: 'auth-02',
    bannerUrl: null,
    youtubeLink: null,
    pdfUrl: null,
    daysAgo: 28,
  },
  {
    title: 'Securing REST APIs with OAuth2 and OIDC',
    summary: 'Implementing token rotation, PKCE flows, and scope-based authorization cleanly.',
    status: 'Published',
    categoryId: 'cat-security',
    authorId: 'auth-05',
    bannerUrl: 'https://cdn.example.com/banners/oauth2-oidc.jpg',
    youtubeLink: null,
    pdfUrl: 'https://cdn.example.com/pdfs/oauth-security.pdf',
    daysAgo: 25,
  },
  {
    title: 'Mastering CSS Grid and Subgrid Layouts',
    summary: 'Modern responsive layout techniques without relying on heavy CSS frameworks.',
    status: 'Published',
    categoryId: 'cat-frontend',
    authorId: 'auth-04',
    bannerUrl: 'https://cdn.example.com/banners/css-subgrid.jpg',
    youtubeLink: 'https://youtube.com/watch?v=css-subgrid-demo',
    pdfUrl: null,
    daysAgo: 22,
  },
  {
    title: 'Event Driven Architecture with Apache Kafka',
    summary: 'Designing resilient consumers, idempotent producers, and dead-letter queues.',
    status: 'Working',
    categoryId: 'cat-backend',
    authorId: 'auth-02',
    bannerUrl: 'https://cdn.example.com/banners/kafka-eda.jpg',
    youtubeLink: null,
    pdfUrl: null,
    daysAgo: 20,
  },
  {
    title: 'Optimizing Docker Images for Fast CI Builds',
    summary: 'Multi-stage builds, layer caching, and distroless base images explained.',
    status: 'Published',
    categoryId: 'cat-devops',
    authorId: 'auth-06',
    bannerUrl: 'https://cdn.example.com/banners/docker-opt.jpg',
    youtubeLink: 'https://youtube.com/watch?v=docker-fast-ci',
    pdfUrl: 'https://cdn.example.com/pdfs/docker-cheatsheet.pdf',
    daysAgo: 18,
  },
  {
    title: 'State Management Patterns in Modern Web Apps',
    summary: 'Comparing server cache libraries, atomic state, and finite state machines.',
    status: 'Open',
    categoryId: 'cat-frontend',
    authorId: 'auth-01',
    bannerUrl: null,
    youtubeLink: null,
    pdfUrl: null,
    daysAgo: 16,
  },
  {
    title: 'Practical Rate Limiting Algorithms Explained',
    summary: 'Token bucket, leaky bucket, and sliding window counters implemented in Redis.',
    status: 'Published',
    categoryId: 'cat-backend',
    authorId: 'auth-05',
    bannerUrl: 'https://cdn.example.com/banners/rate-limiting.jpg',
    youtubeLink: null,
    pdfUrl: null,
    daysAgo: 14,
  },
  {
    title: 'Writing Resilient End to End Tests with Playwright',
    summary: 'Eliminating flaky tests using auto-waiting locators and network mocking.',
    status: 'Published',
    categoryId: 'cat-testing',
    authorId: 'auth-04',
    bannerUrl: null,
    youtubeLink: null,
    pdfUrl: null,
    daysAgo: 12,
  },
  {
    title: 'Kubernetes Autoscaling with KEDA and HPA',
    summary: 'Scaling workloads dynamically based on queue depth and custom Prometheus metrics.',
    status: 'Working',
    categoryId: 'cat-devops',
    authorId: 'auth-06',
    bannerUrl: 'https://cdn.example.com/banners/keda-scaling.jpg',
    youtubeLink: 'https://youtube.com/watch?v=k8s-keda',
    pdfUrl: null,
    daysAgo: 10,
  },
  {
    title: 'GraphQL Schema Design and N Plus One Prevention',
    summary: 'Using DataLoader batching and cursor-based pagination for performant APIs.',
    status: 'Published',
    categoryId: 'cat-backend',
    authorId: 'auth-01',
    bannerUrl: 'https://cdn.example.com/banners/graphql-dataloader.jpg',
    youtubeLink: null,
    pdfUrl: 'https://cdn.example.com/pdfs/graphql-best-practices.pdf',
    daysAgo: 8,
  },
  {
    title: 'WebAssembly Use Cases Beyond the Browser',
    summary: 'Running sandboxed plugins and edge compute workloads with WASI.',
    status: 'Open',
    categoryId: 'cat-systems',
    authorId: 'auth-03',
    bannerUrl: null,
    youtubeLink: null,
    pdfUrl: null,
    daysAgo: 6,
  },
  {
    title: 'Preventing Supply Chain Attacks in NPM Dependencies',
    summary: 'Lockfile verification, provenance attestations, and automated dependency auditing.',
    status: 'Published',
    categoryId: 'cat-security',
    authorId: 'auth-05',
    bannerUrl: 'https://cdn.example.com/banners/npm-security.jpg',
    youtubeLink: 'https://youtube.com/watch?v=npm-supply-chain',
    pdfUrl: null,
    daysAgo: 4,
  },
  {
    title: 'Designing Multi Tenant SaaS Architectures',
    summary: 'Evaluating row-level security, schema-per-tenant, and isolated database models.',
    status: 'Working',
    categoryId: 'cat-database',
    authorId: 'auth-02',
    bannerUrl: null,
    youtubeLink: null,
    pdfUrl: null,
    daysAgo: 2,
  },
  {
    title: 'Real Time Collaborative Editing with CRDTs',
    summary: 'How conflict-free replicated data types synchronize offline-first applications.',
    status: 'Working',
    categoryId: 'cat-systems',
    authorId: 'auth-03',
    bannerUrl: 'https://cdn.example.com/banners/crdt-sync.jpg',
    youtubeLink: 'https://youtube.com/watch?v=crdt-intro',
    pdfUrl: 'https://cdn.example.com/pdfs/crdt-whitepaper.pdf',
    daysAgo: 1,
  },
];

export const dummyArticles: Article[] = rawArticles.map((item) => {
  const createdAt = new Date(Date.now() - item.daysAgo * 24 * 60 * 60 * 1000);
  const updatedAt = new Date(createdAt.getTime() + 3600 * 1000 * 6);
  const publishedAt = item.status === 'Published' ? updatedAt : null;

  return {
    id: crypto.randomUUID(),
    title: item.title,
    slug: generateSlug(item.title),
    summary: item.summary,
    content: generate200WordContent(item.title),
    status: item.status,
    categoryId: item.categoryId,
    authorId: item.authorId,
    bannerUrl: item.bannerUrl,
    youtubeLink: item.youtubeLink,
    pdfUrl: item.pdfUrl,
    publishedAt,
    createdAt,
    updatedAt,
  };
});
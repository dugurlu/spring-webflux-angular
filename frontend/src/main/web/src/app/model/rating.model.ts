export class Rating {
  constructor (
    id: string,
    domain: RatingDomain,
    rule: string,
    fulfillment: number
  ) {}
}


enum RatingDomain {
  REQUIREMENTS,
  ARCHITECTURE,
  CONFIG_MANAGEMENT,
  DEPENDENCY_MANAGEMENT,
  BUILD,
  TEST,
  QA,
  CULTURE,
  RELEASE,
  DEPLOYMENT,
  DATA_MANAGEMENT,
  TECHNOLOGY,
  REPORTING
}

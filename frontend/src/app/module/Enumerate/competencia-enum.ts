export const itensCompetencias: CompetenciaEnum[] = [];

export class CompetenciaEnum {
  static JAVA = new CompetenciaEnum("Java", "JAVA");
  static SPRING_BOOT = new CompetenciaEnum("Spring Boot", "SPRING_BOOT");
  static PYTHON = new CompetenciaEnum("Python", "PYTHON");
  static JAVASCRIPT = new CompetenciaEnum("JavaScript", "JAVASCRIPT");
  static ANGULAR = new CompetenciaEnum("Angular", "ANGULAR");
  static REACT = new CompetenciaEnum("React", "REACT");
  static SQL = new CompetenciaEnum("SQL", "SQL");
  static DATABASE_ADMINISTRATION = new CompetenciaEnum("Administração de Banco de Dados", "DATABASE_ADMINISTRATION");
  static DEVOPS = new CompetenciaEnum("DevOps", "DEVOPS");
  static TESTES_AUTOMATIZADOS = new CompetenciaEnum("Testes Automatizados", "TESTES_AUTOMATIZADOS");
  static GIT = new CompetenciaEnum("Git", "GIT");
  static LINUX = new CompetenciaEnum("Linux", "LINUX");
  static C_SHARP = new CompetenciaEnum("C#", "C_SHARP");
  static GO = new CompetenciaEnum("Go", "GO");
  static NODE_JS = new CompetenciaEnum("Node.js", "NODE_JS");
  static CLOUD_COMPUTING = new CompetenciaEnum("Computação em Nuvem", "CLOUD_COMPUTING");
  static AWS = new CompetenciaEnum("AWS", "AWS");
  static AZURE = new CompetenciaEnum("Azure", "AZURE");
  static MACHINE_LEARNING = new CompetenciaEnum("Machine Learning", "MACHINE_LEARNING");
  static DATA_SCIENCE = new CompetenciaEnum("Ciência de Dados", "DATA_SCIENCE");
  static UI_UX_DESIGN = new CompetenciaEnum("UI/UX Design", "UI_UX_DESIGN");
  static ANDROID = new CompetenciaEnum("Desenvolvimento Android", "ANDROID");
  static IOS = new CompetenciaEnum("Desenvolvimento iOS", "IOS");
  static DOCKER = new CompetenciaEnum("Docker", "DOCKER");
  static KUBERNETES = new CompetenciaEnum("Kubernetes", "KUBERNETES");
  static TDD = new CompetenciaEnum("TDD", "TDD");
  static BDD = new CompetenciaEnum("BDD", "BDD");
  static AGILE = new CompetenciaEnum("Metodologias Ágeis", "AGILE");
  static SCRUM = new CompetenciaEnum("Scrum", "SCRUM");
  static UML = new CompetenciaEnum("UML", "UML");
  static SOLID = new CompetenciaEnum("Princípios SOLID", "SOLID");
  static CI_CD = new CompetenciaEnum("CI/CD", "CI_CD");
  static SECURITY = new CompetenciaEnum("Segurança da Informação", "SECURITY");
  static BIG_DATA = new CompetenciaEnum("Big Data", "BIG_DATA");
  static BLOCKCHAIN = new CompetenciaEnum("Blockchain", "BLOCKCHAIN");

  label: string;
  value: string;

  private constructor(label: string, value: string) {
    this.label = label;
    this.value = value;
    itensCompetencias.push(this);
  }

}
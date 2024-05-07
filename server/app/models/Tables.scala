package model
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends Tables {
  val profile = slick.jdbc.PostgresProfile
}

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Scores.schema ++ Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Scores
   *  @param username Database column username SqlType(varchar), Length(200,true)
   *  @param game Database column game SqlType(varchar)
   *  @param score Database column score SqlType(bpchar) */
  case class ScoresRow(username: String, game: String, score: Char)
  /** GetResult implicit for fetching ScoresRow objects using plain SQL queries */
  implicit def GetResultScoresRow(implicit e0: GR[String], e1: GR[Char]): GR[ScoresRow] = GR{
    prs => import prs._
    ScoresRow.tupled((<<[String], <<[String], <<[Char]))
  }
  /** Table description of table scores. Objects of this class serve as prototypes for rows in queries. */
  class Scores(_tableTag: Tag) extends profile.api.Table[ScoresRow](_tableTag, "scores") {
    def * = (username, game, score).<>(ScoresRow.tupled, ScoresRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(username), Rep.Some(game), Rep.Some(score))).shaped.<>({r=>import r._; _1.map(_=> ScoresRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column username SqlType(varchar), Length(200,true) */
    val username: Rep[String] = column[String]("username", O.Length(200,varying=true))
    /** Database column game SqlType(varchar) */
    val game: Rep[String] = column[String]("game")
    /** Database column score SqlType(bpchar) */
    val score: Rep[Char] = column[Char]("score")

    /** Foreign key referencing Users (database name scores_userid_fkey) */
    lazy val usersFk = foreignKey("scores_userid_fkey", username, Users)(r => r.username, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Scores */
  lazy val Scores = new TableQuery(tag => new Scores(tag))

  /** Entity class storing rows of table Users
   *  @param username Database column username SqlType(varchar), PrimaryKey, Length(200,true)
   *  @param password Database column password SqlType(varchar), Length(200,true) */
  case class UsersRow(username: String, password: String)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[String]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[String], <<[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, "users") {
    def * = (username, password).<>(UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(username), Rep.Some(password))).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column username SqlType(varchar), PrimaryKey, Length(200,true) */
    val username: Rep[String] = column[String]("username", O.PrimaryKey, O.Length(200,varying=true))
    /** Database column password SqlType(varchar), Length(200,true) */
    val password: Rep[String] = column[String]("password", O.Length(200,varying=true))
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}

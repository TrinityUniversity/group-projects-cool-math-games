package models
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
   *  @param userid Database column userid SqlType(varchar), Length(20,true)
   *  @param game Database column game SqlType(varchar), Length(20,true)
   *  @param score Database column score SqlType(varchar), Length(20,true) */
  case class ScoresRow(userid: String, game: String, score: String)
  /** GetResult implicit for fetching ScoresRow objects using plain SQL queries */
  implicit def GetResultScoresRow(implicit e0: GR[String]): GR[ScoresRow] = GR{
    prs => import prs._
    ScoresRow.tupled((<<[String], <<[String], <<[String]))
  }
  /** Table description of table scores. Objects of this class serve as prototypes for rows in queries. */
  class Scores(_tableTag: Tag) extends profile.api.Table[ScoresRow](_tableTag, "scores") {
    def * = (userid, game, score).<>(ScoresRow.tupled, ScoresRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(userid), Rep.Some(game), Rep.Some(score))).shaped.<>({r=>import r._; _1.map(_=> ScoresRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column userid SqlType(varchar), Length(20,true) */
    val userid: Rep[String] = column[String]("userid", O.Length(20,varying=true))
    /** Database column game SqlType(varchar), Length(20,true) */
    val game: Rep[String] = column[String]("game", O.Length(20,varying=true))
    /** Database column score SqlType(varchar), Length(20,true) */
    val score: Rep[String] = column[String]("score", O.Length(20,varying=true))

    /** Foreign key referencing Users (database name scores_userid_fkey) */
    lazy val usersFk = foreignKey("scores_userid_fkey", userid, Users)(r => r.userid, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Scores */
  lazy val Scores = new TableQuery(tag => new Scores(tag))

  /** Entity class storing rows of table Users
   *  @param userid Database column userid SqlType(varchar), PrimaryKey, Length(20,true)
   *  @param username Database column username SqlType(varchar), Length(200,true)
   *  @param password Database column password SqlType(varchar), Length(200,true) */
  case class UsersRow(userid: String, username: String, password: String)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[String]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[String], <<[String], <<[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, "users") {
    def * = (userid, username, password).<>(UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(userid), Rep.Some(username), Rep.Some(password))).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column userid SqlType(varchar), PrimaryKey, Length(20,true) */
    val userid: Rep[String] = column[String]("userid", O.PrimaryKey, O.Length(20,varying=true))
    /** Database column username SqlType(varchar), Length(200,true) */
    val username: Rep[String] = column[String]("username", O.Length(200,varying=true))
    /** Database column password SqlType(varchar), Length(200,true) */
    val password: Rep[String] = column[String]("password", O.Length(200,varying=true))
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}

package models

case class WordlePlayer(name:String,score:Int = 0,numberOfAttempt:Int,isLogin:Boolean = true){
    def addScore(points:Int):WordlePlayer = {
        this.copy(score = this.score + points)
    }
}
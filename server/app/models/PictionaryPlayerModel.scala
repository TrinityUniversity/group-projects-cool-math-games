package models

case class PictionaryPlayer(name:String, score:Int = 0, isDrawing:Boolean = false, isHost:Boolean = false) {
    def addScore(points: Int): PictionaryPlayer = {
        this.copy(score = this.score + points)
    }
}
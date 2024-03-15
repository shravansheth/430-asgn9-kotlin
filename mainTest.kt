import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestCases {
  @Test
  fun test-interpPrimOps(){
    val ast = AppC(
        LamC(listOf("x"), AppC(IdC("+"), listOf(IdC("x"), NumV(1)))),
        listOf(NumV(4))
    )
    assertEquals(NumV(5), interp(ast, topEnv), "This interps to 5")
  }

  @Test
  fun test-interp2args(){
    val ast = AppC(LamC(listOf(IdC("x"), IdC("y")), AppC(IdC("+"), listOf(IdC("x"), IdC("y")))),
    listOf(NumC(5), NumV(7))
    )
    assertEquals(NumV(12), interp(ast, topEnv), "This interps to 12")
  }
}

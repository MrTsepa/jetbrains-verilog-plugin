package com.verilang.commenter

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import com.intellij.codeInsight.generation.actions.CommentByLineCommentAction
import com.verilang.VerilogFileType


class CommenterTest : LightCodeInsightFixtureTestCase() {
    fun `test commenter comment one line`() {
        myFixture.configureByText(VerilogFileType.INSTANCE,"""
module Bar;

    wire bar;<caret>

endmodule
        """.trimIndent())
        val commentAction = CommentByLineCommentAction()
        commentAction.actionPerformedImpl(project, myFixture.editor)
        myFixture.checkResult("""
module Bar;

//    wire bar;
<caret>
endmodule
        """.trimIndent())
    }

    fun `test commenter uncomment one line`() {
        myFixture.configureByText(VerilogFileType.INSTANCE,"""
module Bar;

//    wire bar;<caret>

endmodule
        """.trimIndent())
        val commentAction = CommentByLineCommentAction()
        commentAction.actionPerformedImpl(project, myFixture.editor)
        myFixture.checkResult("""
module Bar;

    wire bar;
<caret>
endmodule
        """.trimIndent())
    }
}
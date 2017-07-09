package br.com.thiengo.todohawk.data

import br.com.thiengo.todohawk.domain.ToDo
import java.util.*


class Mock {
    private fun generateToDo(): ToDo{
        val time = System.currentTimeMillis();
        val tasks = arrayOf(
            "Task 1 - Achieve more by breaking big tasks into smaller sub-tasks (multi-level).",
            "Task 2 - Manage complexity by breaking big projects into smaller sub-projects (multi-level).",
            "Task 3 - Share projects, delegate tasks and discuss details - on any device or platform!",
            "Task 4 - Get notified when important changes happen via emails or push notifications.",
            "Task 5 - Easily add due dates using normal language, such as “monday at 2pm”.",
            "Task 6 - Create repeating due dates naturally like typing “every friday at 8am”."
        )
        val taskPos = Random().nextInt(6)
        val duration = Random().nextInt(7) + 1
        val priority = Random().nextInt(2) + 1

        return ToDo(time, tasks[taskPos], duration, priority)
    }

    fun getToDoList() : List<ToDo>{

        val list = listOf(
            generateToDo(),
            generateToDo(),
            generateToDo(),
            generateToDo(),
            generateToDo(),
            generateToDo(),
            generateToDo(),
            generateToDo(),
            generateToDo(),
            generateToDo(),
            generateToDo(),
            generateToDo(),
            generateToDo(),
            generateToDo()
        )

        return list
    }
}

function showSection(sectionId) {

    const sections =
        document.querySelectorAll(".section");

    sections.forEach(section => {

        section.classList.remove("active");

    });

    document
        .getElementById(sectionId)
        .classList.add("active");


    if (sectionId === "students") {

        loadStudents();

    }


    if (sectionId === "courses") {

        loadCourses();

    }


    if (sectionId === "marks") {

        loadMarks();

    }


    if (sectionId === "performance") {

        loadPerformance();

    }


    if (sectionId === "dashboard") {

        loadStudents();

        loadCourses();

        loadMarks();

    }
}



// =========================
// ADD STUDENT
// =========================

document
    .getElementById("studentForm")
    .addEventListener(
        "submit",
        async function(event) {

            event.preventDefault();


            const formData =
                new URLSearchParams();


            formData.append(
                "id",
                document.getElementById(
                    "studentId"
                ).value
            );


            formData.append(
                "name",
                document.getElementById(
                    "studentName"
                ).value
            );


            formData.append(
                "email",
                document.getElementById(
                    "studentEmail"
                ).value
            );


            const response =
                await fetch(
                    "/add-student",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/x-www-form-urlencoded"
                        },

                        body: formData
                    }
                );


            const result =
                await response.text();


            alert(result);


            if (response.ok) {

                document
                    .getElementById(
                        "studentForm"
                    )
                    .reset();


                showSection(
                    "students"
                );
            }
        }
    );



// =========================
// LOAD STUDENTS
// =========================

async function loadStudents() {

    try {

        const response =
            await fetch(
                "/students"
            );


        const students =
            await response.json();


        const tableBody =
            document.getElementById(
                "studentTableBody"
            );


        tableBody.innerHTML = "";


        students.forEach(student => {

            const row =
                document.createElement(
                    "tr"
                );


            row.innerHTML = `

                <td>
                    ${student.id}
                </td>

                <td>
                    ${student.name}
                </td>

                <td>
                    ${student.email}
                </td>

                <td>

                    <button
                        onclick="editStudent(
                            ${student.id},
                            '${student.name}',
                            '${student.email}'
                        )">

                        Edit

                    </button>

                    <button
                        onclick="deleteStudent(
                            ${student.id}
                        )">

                        Delete

                    </button>

                </td>

            `;


            tableBody.appendChild(row);

        });


        document.getElementById(
            "studentCount"
        ).textContent =
            students.length;


    } catch (error) {

        console.error(error);

    }
}



// =========================
// SEARCH STUDENTS
// =========================

function searchStudents() {

    const search =
        document.getElementById(
            "studentSearch"
        ).value
        .toLowerCase();


    const rows =
        document.querySelectorAll(
            "#studentTableBody tr"
        );


    rows.forEach(row => {

        const text =
            row.textContent
                .toLowerCase();


        if (text.includes(search)) {

            row.style.display = "";

        } else {

            row.style.display = "none";
        }

    });
}



// =========================
// EDIT STUDENT
// =========================

async function editStudent(
    id,
    name,
    email
) {

    const newName =
        prompt(
            "Enter new name:",
            name
        );


    if (newName === null) {

        return;
    }


    const newEmail =
        prompt(
            "Enter new email:",
            email
        );


    if (newEmail === null) {

        return;
    }


    const formData =
        new URLSearchParams();


    formData.append(
        "id",
        id
    );


    formData.append(
        "name",
        newName
    );


    formData.append(
        "email",
        newEmail
    );


    const response =
        await fetch(
            "/update-student",
            {
                method: "POST",

                headers: {
                    "Content-Type":
                        "application/x-www-form-urlencoded"
                },

                body: formData
            }
        );


    const result =
        await response.text();


    alert(result);


    loadStudents();
}



// =========================
// DELETE STUDENT
// =========================

async function deleteStudent(id) {

    if (!confirm(
        "Are you sure you want to delete this student?"
    )) {

        return;
    }


    const formData =
        new URLSearchParams();


    formData.append(
        "id",
        id
    );


    const response =
        await fetch(
            "/delete-student",
            {
                method: "POST",

                headers: {
                    "Content-Type":
                        "application/x-www-form-urlencoded"
                },

                body: formData
            }
        );


    const result =
        await response.text();


    alert(result);


    loadStudents();
}



// =========================
// ADD COURSE
// =========================

document
    .getElementById("courseForm")
    .addEventListener(
        "submit",
        async function(event) {

            event.preventDefault();


            const formData =
                new URLSearchParams();


            formData.append(
                "courseId",
                document.getElementById(
                    "courseId"
                ).value
            );


            formData.append(
                "courseName",
                document.getElementById(
                    "courseName"
                ).value
            );


            formData.append(
                "credits",
                document.getElementById(
                    "courseCredits"
                ).value
            );


            const response =
                await fetch(
                    "/add-course",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/x-www-form-urlencoded"
                        },

                        body: formData
                    }
                );


            const result =
                await response.text();


            alert(result);


            if (response.ok) {

                document
                    .getElementById(
                        "courseForm"
                    )
                    .reset();


                showSection(
                    "courses"
                );
            }
        }
    );



// =========================
// LOAD COURSES
// =========================

async function loadCourses() {

    try {

        const response =
            await fetch(
                "/courses"
            );


        const courses =
            await response.json();


        const tableBody =
            document.getElementById(
                "courseTableBody"
            );


        tableBody.innerHTML = "";


        courses.forEach(course => {

            const row =
                document.createElement(
                    "tr"
                );


            row.innerHTML = `

                <td>
                    ${course.courseId}
                </td>

                <td>
                    ${course.courseName}
                </td>

                <td>
                    ${course.credits}
                </td>

            `;


            tableBody.appendChild(row);

        });


        document.getElementById(
            "courseCount"
        ).textContent =
            courses.length;


    } catch (error) {

        console.error(error);

    }
}



// =========================
// ADD MARK
// =========================

document
    .getElementById("markForm")
    .addEventListener(
        "submit",
        async function(event) {

            event.preventDefault();


            const formData =
                new URLSearchParams();


            formData.append(
                "studentId",
                document.getElementById(
                    "markStudentId"
                ).value
            );


            formData.append(
                "courseId",
                document.getElementById(
                    "markCourseId"
                ).value
            );


            formData.append(
                "marks",
                document.getElementById(
                    "markValue"
                ).value
            );


            const response =
                await fetch(
                    "/add-mark",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/x-www-form-urlencoded"
                        },

                        body: formData
                    }
                );


            const result =
                await response.text();


            alert(result);


            if (response.ok) {

                document
                    .getElementById(
                        "markForm"
                    )
                    .reset();


                showSection(
                    "marks"
                );
            }
        }
    );



// =========================
// LOAD MARKS
// =========================

async function loadMarks() {

    try {

        const response =
            await fetch(
                "/marks"
            );


        const marks =
            await response.json();


        const tableBody =
            document.getElementById(
                "markTableBody"
            );


        tableBody.innerHTML = "";


        marks.forEach(mark => {

            const row =
                document.createElement(
                    "tr"
                );


            row.innerHTML = `

                <td>
                    ${mark.studentName}
                </td>

                <td>
                    ${mark.courseName}
                </td>

                <td>
                    ${mark.marks}
                </td>

                <td>
                    ${mark.grade}
                </td>

            `;


            tableBody.appendChild(row);

        });


        document.getElementById(
            "markCount"
        ).textContent =
            marks.length;


    } catch (error) {

        console.error(error);

    }
}



// =========================
// LOAD PERFORMANCE
// =========================

async function loadPerformance() {

    try {

        const response =
            await fetch(
                "/performance"
            );


        const performance =
            await response.json();


        const tableBody =
            document.getElementById(
                "performanceTableBody"
            );


        tableBody.innerHTML = "";


        performance.forEach(student => {

            const row =
                document.createElement(
                    "tr"
                );


            row.innerHTML = `

                <td>
                    ${student.studentId}
                </td>

                <td>
                    ${student.studentName}
                </td>

                <td>
                    ${student.average}
                </td>

                <td>
                    ${student.grade}
                </td>

            `;


            tableBody.appendChild(row);

        });


    } catch (error) {

        console.error(error);

    }
}



// =========================
// INITIAL LOAD
// =========================

loadStudents();

loadCourses();

loadMarks();


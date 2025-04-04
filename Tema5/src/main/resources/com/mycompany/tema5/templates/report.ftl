<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Image Repository Report</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h1>Image Repository Report</h1>
    <table>
        <tr>
            <th>Name</th>
            <th>Date</th>
            <th>Tags</th>
            <th>File Path</th>
        </tr>
        <#list images as image>
        <tr>
            <td>${image.name()}</td>
            <td>${image.date()}</td>
            <td>${image.tags()?join(", ")}</td>
            <td>${image.filePath()}</td>
        </tr>
        </#list>
    </table>
</body>
</html>
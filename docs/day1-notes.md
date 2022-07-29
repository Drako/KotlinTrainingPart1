# Notes - Day 1

## Visibility

<table>
<thead>
<tr>
<th>Visibility</th>
<th>Java</th>
<th>Kotlin</th>
<th>Comment</th>
</tr>
</thead>
<tbody>
<tr><td>Public</td><td>public</td><td>public (implicit default)</td><td></td></tr>
<tr><td>Private</td><td>private</td><td>private</td><td></td></tr>
<tr><td>Protected</td><td>N/A</td><td>protected</td><td>behaves like in C++</td></tr>
<tr><td>Package-Private</td><td>(implicit default)</td><td>N/A</td><td></td></tr>
<tr><td>Package-Protected</td><td>protected</td><td>N/A</td><td></td></tr>
<tr><td>Internal</td><td>N/A</td><td>internal</td><td>like public, but only for module</td></tr>
</tbody>
</table>

## State

<table>
<tbody>
<tr>
<td style="background-color: mintcream">local/immutable</td><td style="background-color: mintcream">shared/immutable</td>
</tr>
<tr>
<td style="background-color: lightyellow">local/mutable</td><td style="background-color: lightsalmon">shared/mutable</td>
</tr>
</tbody>
</table>

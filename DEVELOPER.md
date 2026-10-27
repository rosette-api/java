## Developer Notes

#### Deprecation Strategies
If we remove a field via deprecation, the field can still be passed in, but we will no longer deserialize it.  As an example see [PR 204](https://github.com/rosette-api/java/pull/204).

#### Building and Releasing
To be updated..

#### Coverage

Run `mvn -Paggregate-coverage clean verify` from the repository root to generate
`coverage/target/site/jacoco-aggregate/index.html` and `jacoco.xml`.
For a library-only build, use `mvn -Paggregate-coverage -pl coverage -am clean verify`.

The report combines coverage for annotations, model, JSON, common, and API classes
with execution data from their tests. This includes model classes exercised by
JSON/API tests, which module-local reports cannot credit. Distribution artifacts
and examples are not included in the aggregate library report.

Both Jenkins pipelines enable this profile.
The CI Sonar build also enables it after its Docker-side `clean`; every analyzed
module imports the same aggregate XML via `sonar.coverage.jacoco.xmlReportPaths`.
The report-only module is skipped by Sonar and deployment. Ordinary builds and
release builds do not include it unless the profile is explicitly enabled.

Do not skip tests or define `coverage.skip`/`jacoco.skip` when collecting coverage.
The inherited coverage profile activates only when `coverage.skip` is absent,
even if that property would otherwise be set to `false`.

#### Internal Releasing

Check https://babelstreet.atlassian.net/wiki/spaces/team7f2bd656bc6e4a81928a92ea96761369/pages/2163736577/Java+binding+internal+release for the internal release process

